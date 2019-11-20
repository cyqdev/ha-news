package com.hengan.news.model.bean;

import java.util.Objects;

public class ScheduleBean {

    public  enum BEAN_STATUS {
        UNDEFINE, ADD,UPDATE,DELETE,KEEP
    }

    private String jobName;
    private String jobGroup;
    private BEAN_STATUS status;

    public ScheduleBean() {
        this.status = BEAN_STATUS.UNDEFINE;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public BEAN_STATUS getStatus() {
        return status;
    }

    public void setStatus(BEAN_STATUS status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleBean scheduleBean = (ScheduleBean) o;
        return Objects.equals(jobGroup, scheduleBean.getJobGroup()) &&
                Objects.equals(jobName, scheduleBean.getJobName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(jobGroup, jobName);
    }
}
