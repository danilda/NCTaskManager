package model;

import model.exception.InvalidIntervalException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Storage class of tasks.
 * @author Vinogradov Max
 */
public class Task implements Cloneable, Serializable {
    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;
    private final int milliseconds = 1000;
    private static final SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor of not-repeated task.
     * @param title task title
     * @param time task time
     */
    public Task(String title, Date time) {
        this.title = title;
        this.time = time;
        start = time;
        end = time;
        this.active = false;
    }

    /**
     * Constructor of repeated task.
     * @param title task title
     * @param start start time of task
     * @param end end time of task
     * @param interval interval of repeating task
     * @throws InvalidIntervalException
     */
    public Task(String title, Date start, Date end, int interval) throws InvalidIntervalException{
        this.title = title;
        this.start = start;
        this.end = end;
        time = start;
        if (interval <= 0) {
            throw new InvalidIntervalException("Interval can not be negative or zero.");
        } else {
            this.interval = interval;
        }
        this.active = false;
    }

    /**
     * @return task title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set new title of task
     * @param title title of task
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return current activity of task
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set new activity of task
     * @param active activity of task
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return task time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Set new time of task
     * @param time time of task
     */
    public void setTime(Date time) {
        this.time = time;
        start = time;
        end = time;
        interval = 0;
    }

    /**
     * @return start time of task
     */
    public Date getStartTime() {
        return start;
    }

    /**
     * @return end time of task
     */
    public Date getEndTime() {
        return end;
    }

    /**
     * @return repeat interval of task
     */
    public int getRepeatInterval() {
        return interval;
    }

    /**
     * Set time of repeated task
     * @param start start time of task
     * @param end end time of task
     * @param interval repeat interval of task
     * @throws InvalidIntervalException
     */
    public void setTime(Date start, Date end, int interval) throws InvalidIntervalException{
        this.start = start;
        this.end = end;
        time = start;
        if (interval <= 0) {
            throw new InvalidIntervalException("Interval can not be negative or zero.");
        } else {
            this.interval = interval;
        }
    }

    /**
     * @return current status of task (repeated/non-repeated)
     */
    public boolean isRepeated() {
        if (interval == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns next time moment relative to the current
     * @param current current time moment
     * @return next time moment relative to the current
     */
    public Date nextTimeAfter(Date current) {
        Date result = null;
        if (isActive()) {
            if (isRepeated()) {
                if (current.compareTo(start) < 0) {
                    result = start;
                } else {
                    if (current.before(end)) {
                        Date point = start;
                        long repeatNumber = ((end.getTime() - start.getTime())) / interval * milliseconds;
                        for (long i = 0; i <= repeatNumber; i++) {
                            Date next = new Date(point.getTime()  + interval * milliseconds);
                            if (current.compareTo(point) >= 0
                                    && current.compareTo(next) < 0
                                    && next.compareTo(end) <= 0) {
                                result = next;
                                break;
                            }
                            point = next;
                        }
                    }
                }
            } else {
                if (current.compareTo(time) < 0) {
                    result = time;
                }
            }
        }
        return result;
    }

    /**
     * Scan an object obj for equivalence with instance of this class
     * @param obj
     * @return equivalence of objects
     */
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        if (this.hashCode() != obj.hashCode()) {
            return false;
        }
        return true;
    }

    /**
     * Calculate hash-code of task object
     * @return hash-code
     */
    public int hashCode() {
        return 3 * title.hashCode() + 3 * time.hashCode() + 2 * start.hashCode() + end.hashCode() + 2 * interval;
    }

    /**
     * @return string representation of object
     */
    @Override
    public String toString() {
        String str;
        if (isRepeated()) {
            String intervakStr = "";
            long temp = interval;
            long time = interval % 60;
            if (time != 0) {
                intervakStr += Long.toString(time);
                if (time < 2) {
                    intervakStr += " second";
                } else {
                    intervakStr += " seconds";
                }
            }
            temp = temp / 60;
            time = temp % 60;
            if (time != 0) {
                if (time < 2) {
                    intervakStr = Long.toString(time) + " minute " + intervakStr;
                } else {
                    intervakStr = Long.toString(time) + " minutes " + intervakStr;
                }
            }
            temp = temp / 60;
            time = temp % 24;
            if (time != 0) {
                if (time < 2) {
                    intervakStr = Long.toString(time) + " hour " + intervakStr;
                } else {
                    intervakStr = Long.toString(time) + " hours " + intervakStr;
                }
            }
            temp = temp / 24;
            if (temp != 0) {
                if (temp < 2) {
                    intervakStr = Long.toString(temp) + " day " + intervakStr;
                } else {
                    intervakStr = Long.toString(temp) + " day " + intervakStr;
                }
            }
            str = '\'' + title + '\'' +
                    ", start = " + sdf.format(start) +
                    ", end = " + sdf.format(end) +
                    ", interval = " + intervakStr;

        } else {
            str = '\'' + title + '\'' +
                    ", time = " + sdf.format(time);
        }
        if (isActive()) {
            str += " | active";
        } else {
            str += " | not active";
        }
        return str;
    }

    /**
     * @return clone of task object
     * @throws CloneNotSupportedException
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        Task clone = (Task)super.clone();
        clone.time = (Date) this.time.clone();
        clone.start = (Date) this.start.clone();
        clone.end = (Date) this.end.clone();
        return clone;
    }
}
