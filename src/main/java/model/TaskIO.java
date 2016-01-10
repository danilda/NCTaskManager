package model;

import model.exception.InvalidIntervalException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class that provides and encapsulates service methods for working with input/output process.
 * @author Vinogradov Max
 */
public class TaskIO {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
    private static final String[] TIMES_PARTS = {" day", " hour", " minute", " second"};

    /**
     * Writes tasks from the list of tasks in a stream in binary format.
     * @param tasks
     * @param out
     * @throws IOException
     */
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(out);
        try {
            outputStream.writeInt(tasks.size());
            for (Task t : tasks) {
                outputStream.writeInt(t.getTitle().length());
                outputStream.writeUTF(t.getTitle());
                outputStream.writeBoolean(t.isActive());
                outputStream.writeInt(t.getRepeatInterval());
                if (t.isRepeated()) {
                    outputStream.writeLong(t.getStartTime().getTime());
                    outputStream.writeLong(t.getEndTime().getTime());
                } else {
                    outputStream.writeLong(t.getTime().getTime());
                }
            }
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }

    /**
     * Read tasks from stream in a the list of tasks in binary format.
     * @param tasks
     * @param in
     * @throws IOException
     * @throws InvalidIntervalException
     */
    public static void read(TaskList tasks, InputStream in) throws IOException, InvalidIntervalException {
        DataInputStream inputStream = new DataInputStream(in);
        try {
            int tasksCount = inputStream.readInt();
            for (int i = 0; i < tasksCount; i++) {
                int len = inputStream.readInt();
                String title = inputStream.readUTF();
                boolean activity = inputStream.readBoolean();
                int interval = inputStream.readInt();
                Date startTime = new Date(inputStream.readLong());
                Task tempTask = null;
                if (interval > 0) {
                    Date endTime = new Date(inputStream.readLong());
                    tempTask = new Task(title, startTime, endTime, interval);
                } else {
                    tempTask = new Task(title, startTime);
                }
                tempTask.setActive(activity);
                tasks.add(tempTask);
            }
        } finally {
            inputStream.close();
        }
    }

    /**
     * Write task from the list to the file in binary format.
     * @param tasks
     * @param file
     * @throws IOException
     */
    void writeBinary(TaskList tasks, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try {
            write(tasks,fos);
        } finally {
            fos.close();
        }
    }

    /**
     * Read task from the file to the list in binary format.
     * @param tasks
     * @param file
     * @throws IOException
     * @throws InvalidIntervalException
     */
    void readBinary(TaskList tasks, File file) throws IOException, InvalidIntervalException {
        FileInputStream fis = new FileInputStream(file);
        try {
            read(tasks, fis);
        } finally {
            fis.close();
        }
    }

    /**
     * Writes tasks from the list of tasks in a stream in text format.
     * @param tasks
     * @param out
     * @throws IOException
     */
    public static void write(TaskList tasks, Writer out) throws IOException {
        BufferedWriter bw = new BufferedWriter(out);
        try {
            int max = tasks.size() - 1;
            int i = 0;
            for (Task t : tasks) {
                bw.append(makeStringFromTask(t));
                bw.append((i != max ? ";" : "."));
                bw.newLine();
                i++;
            }
        } finally {
            bw.flush();
        }
    }

    /**
     * Read tasks from stream in a the list of tasks in text format.
     * @param tasks
     * @param in
     * @throws IOException
     * @throws InvalidIntervalException
     */
    public static void read(TaskList tasks, Reader in) throws IOException, InvalidIntervalException {
        BufferedReader br = new BufferedReader(in);
        try {
            if (br.ready()) {
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    tasks.add(makeTaskFromString(line));
                    i++;
                    if (line.charAt(line.length() - 1) == '.') {
                        break;
                    }
                }
            } else {
                throw new IOException("Stream is not ready to be read!");
            }
        } finally {
            br.close();
        }
    }

    /**
     * Write task from the list to the file in text format.
     * @param tasks
     * @param file
     * @throws IOException
     */
    public static void writeText(TaskList tasks, File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        try {
            write(tasks, fw);
        } finally {
            fw.close();
        }
    }

    /**
     * Read task from the file to the list in text format.
     * @param tasks
     * @param file
     * @throws IOException
     * @throws InvalidIntervalException
     */
    public static void readText(TaskList tasks, File file) throws IOException, InvalidIntervalException {
        FileReader fr = new FileReader(file);
        try {
            read(tasks, fr);
        } finally {
            fr.close();
        }

    }

    /*------------HELP METHODS------------*/

    private static String makeStringFromTask(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append('"');
        String temp = new String(task.getTitle());
        temp.replaceAll("\"", "\"\"");
        sb.append(temp);
        sb.append('"');
        sb.append(" ");
        if (task.isRepeated()) {
            sb.append("from ");
        } else {
            sb.append("at ");
        }
        sb.append(sdf.format(task.getStartTime()));
        if (task.isRepeated()) {
            sb.append(" to ");
            sb.append(sdf.format(task.getEndTime()));
            sb.append(" every ");
            sb.append(makeInterval(task.getRepeatInterval()));
        }
        if (! task.isActive()) {
            sb.append(" inactive");
        }
        return sb.toString();
    }

    private static String makeInterval(int interval) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int[] val = new int[4];
        int temp = interval;
        val[0] = temp / (24*60*60);
        temp = temp % (24*60*60);
        val[1] = temp / (60*60);
        temp = temp % (60*60);
        val[2] = temp / 60;
        temp = temp % 60;
        val[3] = temp;
        boolean first = true;
        for (int i = 0; i < val.length; i++) {
            if (val[i] != 0) {
                if (!first) {
                    sb.append(' ');
                }
                sb.append(val[i]);
                sb.append(TIMES_PARTS[i]);
                if (val[i] > 1) {
                    sb.append('s');
                }
                first = false;
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private static Task makeTaskFromString(String str) throws InvalidIntervalException {
        String title = "";
        boolean activity = true;
        Date time = new Date(0);
        Date start = new Date(0);
        Date end = new Date(0);
        int interval = 0;
        StringBuilder sb = new StringBuilder(str);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        if (sb.charAt(sb.length() - 1) == 'e') {
            activity = false;
            sb.delete(sb.length() - 10, sb.length());
        } else {
            sb.delete(sb.length() - 1, sb.length());
        }
        boolean isRepeat = false;
        interval = parceInterval(sb);
        if (interval != 0) {
            isRepeat = true;
        }
        time = parseData(sb);
        end = time;
        start = time;
        if (sb.charAt(sb.length() - 1) == 't') {
            sb.delete(sb.length() - 4, sb.length());
            title = deleteBreackets(sb);
        } else {
            sb.delete(sb.length() - 4, sb.length());
            start = parseData(sb);
            sb.delete(sb.length() - 6, sb.length());
            title = deleteBreackets(sb);
        }
        Task task;
        if (isRepeat) {
            task = new Task(title, start, end, interval);
        } else {
            task = new Task(title, time);
        }
        task.setActive(activity);
        return task;
    }

    private static int parceInterval(StringBuilder sb) {
        int interval = 0, temp = 0;
        if (sb.charAt(sb.length() - 1) == 's') {
            sb.delete(sb.length() - 1, sb.length());
        }
        if (sb.charAt(sb.length() - 1) == 'd') {
            sb.delete(sb.length() - 7, sb.length());
            int inc = 1;
            while (sb.charAt(sb.length() - 1) != ' ' &&
                    sb.charAt(sb.length() - 1) != '[') {
                interval += Integer.parseInt(sb.substring(sb.length() - 1)) * inc;
                inc *= 10;
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        deleteGarbage(sb);
        if (sb.charAt(sb.length() - 1) == 'e') {
            sb.delete(sb.length() - 7, sb.length());
            int inc = 1;
            while (sb.charAt(sb.length() - 1) != ' ' &&
                    sb.charAt(sb.length() - 1) != '[') {
                temp += Integer.parseInt(sb.substring(sb.length() - 1)) * inc;
                inc *= 10;
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        deleteGarbage(sb);
        interval += temp * 60;
        temp = 0;
        if (sb.charAt(sb.length() - 1) == 'r') {
            sb.delete(sb.length() - 5, sb.length());
            int inc = 1;
            while (sb.charAt(sb.length() - 1) != ' ' &&
                    sb.charAt(sb.length() - 1) != '[') {
                temp += Integer.parseInt(sb.substring(sb.length() - 1)) * inc;
                inc *= 10;
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        interval += temp * 60 * 60;
        temp = 0;
        deleteGarbage(sb);
        if (sb.charAt(sb.length() - 1) == 'y' &&
                sb.charAt(sb.length() - 2) == 'a') {
            sb.delete(sb.length() - 4, sb.length());
            int inc = 1;
            while (sb.charAt(sb.length() - 1) != ' ' &&
                    sb.charAt(sb.length() - 1) != '[') {
                temp += Integer.parseInt(sb.substring(sb.length() - 1)) * inc;
                inc *= 10;
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        interval += temp * 60 * 60 * 24;
        temp = 0;
        deleteGarbage(sb);
        if (sb.charAt(sb.length() - 1) == 'y' &&
                sb.charAt(sb.length() - 2) == 'r') {
            sb.delete(sb.length() - 7, sb.length());
        }
        return interval;
    }

    private static void deleteGarbage(StringBuilder sb ) {
        while (sb.charAt(sb.length() - 1) == ' ' ||
                sb.charAt(sb.length() - 1) == '[' ||
                sb.charAt(sb.length() - 1) == 's') {
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    private static Date parseData(StringBuilder sb) {
        long miliseconds = 0;
        int temp = 0;
        Date dt = new Date(0);
        int inc = 1;
        for (int i = 0; i < 3; i++) {
            miliseconds += Long.parseLong(sb.substring(sb.length() - 1)) * inc;
            inc *= 10;
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.deleteCharAt(sb.length() - 1);
        temp = Integer.parseInt(sb.substring(sb.length() - 1));
        sb.deleteCharAt(sb.length() - 1);
        temp += 10 * Integer.parseInt(sb.substring(sb.length() - 1));
        sb.delete(sb.length() - 2, sb.length());
        dt.setSeconds(temp);
        temp = Integer.parseInt(sb.substring(sb.length() - 1));
        sb.deleteCharAt(sb.length() - 1);
        temp += 10 * Integer.parseInt(sb.substring(sb.length() - 1));
        sb.delete(sb.length() - 2, sb.length());
        dt.setMinutes(temp);
        temp = Integer.parseInt(sb.substring(sb.length() - 1));
        sb.deleteCharAt(sb.length() - 1);
        temp += 10 * Integer.parseInt(sb.substring(sb.length() - 1));
        sb.delete(sb.length() - 2, sb.length());
        dt.setHours(temp);
        temp = Integer.parseInt(sb.substring(sb.length() - 1));
        sb.deleteCharAt(sb.length() - 1);
        temp += 10 * Integer.parseInt(sb.substring(sb.length() - 1));
        sb.delete(sb.length() - 2, sb.length());
        dt.setDate(temp);
        temp = Integer.parseInt(sb.substring(sb.length() - 1));
        sb.deleteCharAt(sb.length() - 1);
        temp += 10 * Integer.parseInt(sb.substring(sb.length() - 1));
        sb.delete(sb.length() - 2, sb.length());
        dt.setMonth(temp - 1);
        inc = 1;
        temp = 0;
        for (int i = 0; i < 4; i++) {
            temp += inc * Integer.parseInt(sb.substring(sb.length() - 1));
            sb.deleteCharAt(sb.length() - 1);
            inc *= 10;
        }
        dt.setYear(temp - 1900);
        sb.delete(sb.length() - 2, sb.length());
        dt.setTime(dt.getTime() + miliseconds);
        return dt;
    }

    private static String deleteBreackets(StringBuilder sb) {
        return sb.toString().replaceAll("\"\"", "\"");
    }
}
