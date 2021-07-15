package com.example.projecttravel;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SnapHelp extends LinearSnapHelper {

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {

        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }

        final View currentView = findSnapView(layoutManager);

        if (currentView == null) {
            return RecyclerView.NO_POSITION;
        }

        LinearLayoutManager myLayoutManager = (LinearLayoutManager) layoutManager;

        int position1 = myLayoutManager.findFirstVisibleItemPosition();
        int position2 = myLayoutManager.findLastVisibleItemPosition();

        int currentPosition;
        currentPosition = layoutManager.getPosition(currentView);

        if (velocityX > 100) {
            currentPosition = position2;
        } else if (velocityX < 100) {
            currentPosition = position1;
        }

        if (currentPosition != RecyclerView.NO_POSITION) {
            return currentPosition;
        }
        return RecyclerView.NO_POSITION;

    }
}
