package com.yiwucheguanjia.carmgr.animation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yiwucheguanjia.carmgr.R;

@SuppressLint("ValidFragment")
public class DiologLoading extends DialogFragment
{

	private String typeLoading = "Loading";
	public DiologLoading(String typeLoading) {
		// TODO Auto-generated constructor stub
		this.typeLoading = typeLoading;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_loading, null);
		TextView title = (TextView) view
				.findViewById(R.id.id_dialog_loading_msg);
		title.setText(typeLoading);
		Dialog dialog = new Dialog(getActivity(), R.style.dialog);
		dialog.setContentView(view);
		return dialog;
	}
}
