package com.micmon.retailshop.domain.api;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.micmon.retailshop.R;
import com.micmon.retailshop.domain.mock.FakeWebServer;
import com.micmon.retailshop.util.AppConstants;
import com.micmon.retailshop.util.Utils;
import com.micmon.retailshop.util.Utils.AnimationType;
import com.micmon.retailshop.view.activities.ECartHomeActivity;
import com.micmon.retailshop.view.adapter.CategoryListAdapter;
import com.micmon.retailshop.view.adapter.CategoryListAdapter.OnItemClickListener;
import com.micmon.retailshop.view.fragment.ProductOverviewFragment;

/**
 * The Class ImageLoaderTask.
 */
public class ProductCategoryLoaderTask extends AsyncTask<String, Void, Void> {

	private static final int NUMBER_OF_COLUMNS = 2;
	private Context context;
	private RecyclerView recyclerView;

	public ProductCategoryLoaderTask(RecyclerView listView, Context context) {

		this.recyclerView = listView;
		this.context = context;
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();

		if (null != ((ECartHomeActivity) context).getProgressBar())
			((ECartHomeActivity) context).getProgressBar().setVisibility(
					View.VISIBLE);

	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		if (null != ((ECartHomeActivity) context).getProgressBar())
			((ECartHomeActivity) context).getProgressBar().setVisibility(
					View.GONE);

		if (recyclerView != null) {
			CategoryListAdapter simpleRecyclerAdapter = new CategoryListAdapter(
					context);

			recyclerView.setAdapter(simpleRecyclerAdapter);

			simpleRecyclerAdapter
					.SetOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(View view, int position) {

							AppConstants.CURRENT_CATEGORY = position;

							Utils.switchFragmentWithAnimation(
									R.id.frag_container,
									new ProductOverviewFragment(),
									((ECartHomeActivity) context), null,
									AnimationType.SLIDE_LEFT);

						}
					});
		}

	}

	@Override
	protected Void doInBackground(String... params) {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		FakeWebServer.getFakeWebServer().addCategory();

		return null;
	}

}