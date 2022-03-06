package com.example.socketserver.common;

import com.example.socketserver.dto.TableDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GlobalVariables {

    private static final Set<String> sessions = new HashSet<>();
    private static List<TableDto> serverTables = new ArrayList<>();

    public static List<TableDto> getTables(){
        return serverTables;
    }

    public static int getSessionCount(){
        return sessions.size();
    }

    public static void addSession(String sessionId) {
        sessions.add(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public static void initTables(List<TableDto> tables) {
        if (sessions.size() == 1) {
            serverTables = tables;
        }
    }

    public static void changeTargetFireStatus(final Long targetId) {
        serverTables.forEach(table -> {
            if (table.getId().equals(targetId)) {
                table.changeFireStatus();
            }
        });
    }

}
