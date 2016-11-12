package com.yiwucheguanjia.merchantcarmgr.animation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yiwucheguanjia.merchantcarmgr.R;

@SuppressLint("ValidFragment")
public class DialogLoading extends DialogFragment
{

	private String typeLoading = "Loading";
	public DialogLoading(String typeLoading) {
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
//		dialog.getWindow().getAttributes();

		WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
		lp.dimAmount=0.5f;

		dialog.getWindow().setAttributes(lp);
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		return dialog;
	}
}
