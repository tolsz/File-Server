package server;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Operations {
    Map<Integer, String> idMap;
    final String path =  System.getProperty("user.dir") + "/src/server/data/";
    //my path
    //final String path = System.getProperty("user.dir") + "/File Server/task/src/server/data/";
    int lastID;

    public Operations() {
        try {
            idMap = (Map<Integer, String>) SerializationUtils.deserialize(path + "map.data");
            lastID = 1;
            for (var entry : idMap.entrySet()) {
                lastID++;
            }
        } catch (IOException | ClassNotFoundException e) {
            lastID = 1;
        }

        if (idMap == null) {
            idMap = new LinkedHashMap<>();
        }
    }

    public void put(DataInputStream input, DataOutputStream dos, String fileName) {
        File file = new File(path + fileName);
        try {
            int length = input.readInt();
            byte[] message = new byte[length];
            input.readFully(message, 0, message.length);
            if (file.exists()) {
                dos.writeUTF("403");
                return;
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(message);
            dos.writeUTF("200 " + lastID);
            idMap.put(lastID, fileName);
            lastID++;
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get(DataOutputStream output, String idOrFileName, boolean isFile) {
        if (!isFile) {
            int id = Integer.parseInt(idOrFileName);
            idOrFileName = idMap.get(id);
        }
        File file = new File(path + idOrFileName);
        try {
            if (!file.exists()) {
                output.writeUTF("404");
                return;
            } else {
                output.writeUTF("200");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int length = (int) file.length();
        byte[] message = new byte[length];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(message);
            output.writeInt(length);
            output.write(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(DataOutputStream output, String idOrFileName, boolean isFile) {
        int id = -1;
        if (!isFile) {
            id = Integer.parseInt(idOrFileName);
            idOrFileName = idMap.get(id);
        } else {
            for (var entry : idMap.entrySet()) {
                if (entry.getValue().equals(idOrFileName)) {
                    id = entry.getKey();
                }
            }
        }
        File file = new File(path + idOrFileName);
        try {
            if (!file.exists() || !idMap.containsKey(id)) {
                output.writeUTF("404");
            } else {
                file.delete();
                idMap.remove(id);
                output.writeUTF("200");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            SerializationUtils.serialize(idMap, path + "map.data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
