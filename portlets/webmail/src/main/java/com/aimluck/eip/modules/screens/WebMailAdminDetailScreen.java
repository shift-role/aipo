/*
 * Aipo is a groupware program developed by TOWN, Inc.
 * Copyright (C) 2004-2015 TOWN, Inc.
 * http://www.aipo.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aimluck.eip.modules.screens;

import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;

import com.aimluck.eip.util.ALEipUtils;
import com.aimluck.eip.webmail.WebMailAccountSelectData;
import com.aimluck.eip.webmail.util.WebMailUtils;

/**
 * 管理者用メールアカウントの詳細画面を処理するクラスです。 <br />
 * 
 */
public class WebMailAdminDetailScreen extends ALVelocityScreen {

  /** logger */
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(WebMailAdminDetailScreen.class.getName());

  /**
   * 
   * @param rundata
   * @param context
   * @throws Exception
   */
  @Override
  protected void doOutput(RunData rundata, Context context) throws Exception {

    try {
      WebMailAccountSelectData detailData = new WebMailAccountSelectData();
      detailData.initField();
      detailData.doViewDetail(this, rundata, context);
      setTemplate(
        rundata,
        context,
        "portlets/html/ajax-webmail-account-detail-admin.vm");

    } catch (Exception ex) {
      logger.error("[WebMailAdminScreen] Exception.", ex);
      ALEipUtils.redirectDBError(rundata);
    }
  }

  /**
   * @return
   */
  @Override
  protected String getPortletName() {
    return WebMailUtils.WEBMAIL_ADMIN_PORTLET_NAME;
  }

}
