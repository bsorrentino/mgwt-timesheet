package org.bsc.client.pulltorefresh;

import java.util.List;

import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel.Pullhandler;

public interface PullToRefreshDisplay<T> {

		public void render(List<T> topics);

		public void setHeaderPullHandler(Pullhandler pullHandler);

		public void setFooterPullHandler(Pullhandler pullHandler);

		public PullArrowWidget getPullHeader();

		public PullArrowWidget getPullFooter();

		public void refresh();

		public HasRefresh getPullPanel();

}
