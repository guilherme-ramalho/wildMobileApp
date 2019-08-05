package br.com.nanothings.wildmobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.model.ModalidadeApostas;
import butterknife.BindView;

public class InicioFragment extends Fragment {
    @BindView(R.id.spinnerModalidade) Spinner modalidadeSpinner;
    private ModalidadeApostas modalidadeApostas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.menu_home);

        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    private void setSpinnerModalidade() {

    }
}
