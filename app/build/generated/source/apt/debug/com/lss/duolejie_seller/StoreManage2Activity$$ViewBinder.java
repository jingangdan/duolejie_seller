// Generated code from Butter Knife. Do not modify!
package com.lss.duolejie_seller;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class StoreManage2Activity$$ViewBinder<T extends com.lss.duolejie_seller.StoreManage2Activity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558551, "field 'imSmBack' and method 'onClick'");
    target.imSmBack = finder.castView(view, 2131558551, "field 'imSmBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558552, "field 'gridView'");
    target.gridView = finder.castView(view, 2131558552, "field 'gridView'");
    view = finder.findRequiredView(source, 2131558553, "field 'etSm'");
    target.etSm = finder.castView(view, 2131558553, "field 'etSm'");
    view = finder.findRequiredView(source, 2131558554, "field 'etSmPhone'");
    target.etSmPhone = finder.castView(view, 2131558554, "field 'etSmPhone'");
    view = finder.findRequiredView(source, 2131558555, "field 'butSm' and method 'onClick'");
    target.butSm = finder.castView(view, 2131558555, "field 'butSm'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.imSmBack = null;
    target.gridView = null;
    target.etSm = null;
    target.etSmPhone = null;
    target.butSm = null;
  }
}
