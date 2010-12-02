/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2010 Aimluck,Inc.
 * http://aipostyle.com/
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

package com.aimluck.eip.util;

import java.security.SecureRandom;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.jetspeed.util.Base64;
import org.apache.turbine.util.DynamicURI;

/**
 * Aimluck EIP のユーティリティクラスです。 <br />
 * 
 */
public class ALCommonUtils {

  /** logger */
  private static final JetspeedLogger logger =
    JetspeedLogFactoryService.getLogger(ALCommonUtils.class.getName());

  /** 乱数生成用アルゴリズム（SHA1） */
  public static final String DEF_RANDOM_ALGORITHM = "SHA1PRNG";

  /** 乱数生成用アルゴリズム（生成するバイト配列の長さ） */
  public static final int DEF_RANDOM_LENGTH = 16;

  /** 乱数生成機保持用 */
  private static SecureRandom random = getSecureRandom();

  public static String escapeXML(String string) {
    return StringEscapeUtils.escapeXml(string);
  }

  public static String escapeXML(DynamicURI uri) {
    return StringEscapeUtils.escapeXml(uri.toString());
  }

  /**
   * * 長いアルファベットのテキストを自動的に折り返すヘルパー
   * 
   * @subpackage helper
   * @param string
   *            $text
   * @param int
   *            $step
   * @return string
   */
  public static String replaceToAutoCRString(String str) {
    if (str == null || "".equals(str)) {
      return "";
    }
    StringBuffer res = new StringBuffer("");
    int step = 6;
    int size = str.length();
    int count = size / step;
    int j;
    for (int i = 0; i < count; i++) {
      j = i * step;
      res.append(str.substring(j, j + step)).append("<wbr/>");
    }
    if (count * step < size) {
      res.append(str.substring(count * step));
    }
    return res.toString();
  }

  /**
   * * 長いアルファベットのテキストを自動的に折り返すヘルパー
   * 
   * @subpackage helper
   * @param string
   *            $text
   * @param int
   *            $step
   * @return string
   */
  public static String replaceToAutoCRChild(String str) {
    if (str == null || "".equals(str)) {
      return "";
    }

    StringBuffer res = null;
    String head, body, tail;
    int findex = str.indexOf("&");
    int lindex = str.indexOf(";");
    if ((findex == -1) && (lindex == -1)) {
      return replaceToAutoCRString(str);
    } else if (findex == -1 || findex > lindex) {
      // ";"のみ含まれる場合
      head = str.substring(0, lindex);
      body = str.substring(lindex, lindex + 1);
      tail = str.substring(lindex + 1);
      res = new StringBuffer();
      res.append(replaceToAutoCRString(head));
      res.append(body);
      res.append(replaceToAutoCR(tail));
    } else if (lindex == -1) {
      // "&"のみ含まれる場合
      head = str.substring(0, findex);
      body = str.substring(findex, findex + 1);
      tail = str.substring(findex + 1);
      res = new StringBuffer();
      res.append(replaceToAutoCRString(head));
      res.append(body);
      res.append(replaceToAutoCR(tail));
    } else {
      head = str.substring(0, findex);
      body = str.substring(findex, lindex + 1);
      tail = str.substring(lindex + 1);
      res = new StringBuffer();
      res.append(replaceToAutoCRString(head));
      res.append(body);
      res.append(replaceToAutoCR(tail));
    }

    return res.toString();
  }

  /**
   * * 長いアルファベットのテキストを自動的に折り返すヘルパー
   * 
   * @subpackage helper
   * @param string
   *            $text
   * @param int
   *            $step
   * @return string
   */
  public static String replaceToAutoCR(String str) {
    if (str == null || "".equals(str)) {
      return "";
    }

    StringBuffer res = null;
    String head, body, tail;
    int findex = str.indexOf("<");
    int lindex = str.indexOf(">");
    if ((findex == -1) && (lindex == -1)) {
      return replaceToAutoCRChild(str);
    } else if (findex == -1 || findex > lindex) {
      // ">"のみ含まれる場合
      head = str.substring(0, lindex);
      body = str.substring(lindex, lindex + 1);
      tail = str.substring(lindex + 1);
      res = new StringBuffer();
      res.append(replaceToAutoCRChild(head));
      res.append(body);
      res.append(replaceToAutoCR(tail));
    } else if (lindex == -1) {
      // "<"のみ含まれる場合
      head = str.substring(0, findex);
      body = str.substring(findex, findex + 1);
      tail = str.substring(findex + 1);
      res = new StringBuffer();
      res.append(replaceToAutoCRChild(head));
      res.append(body);
      res.append(replaceToAutoCR(tail));
    } else {
      head = str.substring(0, findex);
      body = str.substring(findex, lindex + 1);
      tail = str.substring(lindex + 1);
      res = new StringBuffer();
      res.append(replaceToAutoCRChild(head));
      res.append(body);
      res.append(replaceToAutoCR(tail));
    }

    return res.toString();
  }

  /**
   * 第二引数で指定した長さで、第一引数の文字列を丸める。
   * 
   * @param src
   *            元の文字列
   * @param length
   *            丸めの長さ
   * @return ●処理後の文字列
   */
  public static String compressString(String src, int length) {
    if (src == null || src.length() == 0 || length <= 0) {
      return src;
    }

    String subject;
    if (src.length() > length) {
      subject = src.substring(0, length);
      subject += "・・・";
    } else {
      subject = src;
    }
    return subject;
  }

  /**
   * ランダムなセキュリティIDを生成する。
   * 
   * @return string Base64エンコードされた文字列
   */
  public static String getSecureRandomBase64() {
    String res = null;
    try {
      if (null == random) {
        return null;
      }

      byte b[] = new byte[DEF_RANDOM_LENGTH];
      random.nextBytes(b);
      res = Base64.encodeAsString(b);
    } catch (Exception e) {
      logger.error("Exception", e);
      return null;
    }

    return res;
  }

  /**
   * セキュリティID生成用のSecureRandomを生成します。
   * 
   * @return random セキュリティID生成用のSecureRandom
   */
  public static SecureRandom getSecureRandom() {
    SecureRandom random = null;
    try {
      random = SecureRandom.getInstance(DEF_RANDOM_ALGORITHM);
      byte seed[] = random.generateSeed(DEF_RANDOM_LENGTH);
      random.setSeed(seed);
    } catch (Exception e) {
      logger.error("Exception", e);
      return null;
    }

    return random;
  }

  /**
   * URL文字列にスキーマ部分が含まれていなかった場合、先頭に付加した物を返します
   * 
   * @param url
   * @return
   */
  public static String normalizeURL(String url) {
    String res = url;
    if (!res.contains("://")) {
      res = "http://" + res;
    }
    return res;
  }
}
