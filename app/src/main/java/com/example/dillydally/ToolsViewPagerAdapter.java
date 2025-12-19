
package com.example.dillydally;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ToolsViewPagerAdapter extends FragmentStateAdapter {

    public ToolsViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new CalculatorFragment();
        } else {
            return new StopwatchFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
