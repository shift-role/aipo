/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
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
 *
 * General Purpose Database Portlet was developed by Advance,Inc.
 * http://www.a-dvance.co.jp/
 */

package com.aimluck.eip.modules.screens;

import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.turbine.util.RunData;

import com.aimluck.eip.cayenne.om.portlet.EipTGpdbRecordFile;
import com.aimluck.eip.gpdb.util.GpdbUtils;

/**
 * 汎用データベースレコードの添付ファイルのサムネイルを処理するクラスです。
 */
public class GpdbRecordFileThumbnailScreen extends FileuploadThumbnailScreen {

  /** logger */
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(GpdbRecordFileThumbnailScreen.class.getName());

  /**
   * 
   * @param rundata
   *          RunData
   * @throws Exception
   *           例外
   */
  @Override
  protected void doOutput(RunData rundata) throws Exception {
    try {
      EipTGpdbRecordFile file = GpdbUtils.getEipTGpdbRecordFile(rundata);

      super.setFile(file.getFileThumbnail());
      super.setFileName(file.getFileName());
      super.doOutput(rundata);
    } catch (Exception e) {
      logger.error("[ERROR]", e);
    }
  }
}
