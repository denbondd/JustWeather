package com.denbondd.justweather.ui.activities.main;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.denbondd.justweather.R;
import com.denbondd.justweather.models.City;
import com.denbondd.justweather.ui.adapters.navitems.NavItemsRVAdapter;
import com.denbondd.justweather.ui.base.BaseVM;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainVM extends BaseVM {
    public MutableLiveData<String> currentPage = new MutableLiveData<>();

    public LiveData<List<City>> getAllLD() {
        return appDatabase.cityDao().getAll();
    }

    public long getCurrentId() {
        return appDatabase.cityDao().getCurrent().getId();
    }

    public void deleteCity(City city) {
        new Thread(() -> appDatabase.cityDao().delete(city)).start();
    }

    public void undoDelete(City city) {
        new Thread(() -> appDatabase.cityDao().insert(city)).start();
    }

    public boolean onMoveNavDrawer(NavItemsRVAdapter adapter, RecyclerView.ViewHolder dragged, RecyclerView.ViewHolder target) {
        int positionDragged = dragged.getAdapterPosition();
        int positionTarget = target.getAdapterPosition();

        if (positionTarget == positionDragged) return false;

        adapter.setNeedToNotify(false);

        City cityDragged = adapter.getCities().get(positionDragged);
        City cityTarget = adapter.getCities().get(positionTarget);

        long cityViewHolderId = cityDragged.getId();
        cityDragged.setId(cityTarget.getId());
        cityTarget.setId(cityViewHolderId);

        new Thread(() -> appDatabase.cityDao().updateAll((List<City>) adapter.getCities())).start();

        adapter.notifyItemMoved(positionDragged, positionTarget);
        new Handler().postDelayed(() -> adapter.setNeedToNotify(true), 1000);
        return false;
    }

    public City onSwipedNavDrawer(NavItemsRVAdapter adapter, RecyclerView.ViewHolder viewHolder) {
        City city = adapter.getCities().get(viewHolder.getAdapterPosition());
        if (adapter.getCities().size() == 1) {
            Toast.makeText(getContext(), R.string.deleteLastCity, Toast.LENGTH_SHORT).show();
            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
            return city;
        } else if (city.isCurrent()) {
            Toast.makeText(getContext(), R.string.snackbarCurrentCity, Toast.LENGTH_SHORT).show();
            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
            return city;
        }
        deleteCity(city);
        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        return city;
    }

    public void showUndoSnackbar(View view, City city, int position) {
        Snackbar snackbar = Snackbar.make(view, getContext().getString(R.string.snackbarUndoText, city.getName()), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, v -> undoDelete(city));
        snackbar.show();
    }
}
