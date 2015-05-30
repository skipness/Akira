package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skip.akira.R;

public class BlankFragment extends Fragment {

    public BlankFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("BLANK VIEW HERE---- ", "yay");
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }
}
