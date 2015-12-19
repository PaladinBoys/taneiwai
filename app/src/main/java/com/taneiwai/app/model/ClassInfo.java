package com.taneiwai.app.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by weiTeng on 15/12/19.
 */
public class ClassInfo implements Serializable {

    /** 类型 */
    public int type;
    public String typeName;

    /** 时间 */
    public String time;

    public String content;

    public ArrayList<Subject> subjects;

    public boolean showApproval;
    public int watchCount;
    public int approvalCount;
}
