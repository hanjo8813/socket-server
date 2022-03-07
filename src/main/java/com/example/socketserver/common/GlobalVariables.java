package com.example.socketserver.common;

import com.example.socketserver.dto.TableDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

// 싱글톤 패턴 적용
public class GlobalVariables {

    // 멀티스레드 thread safe 처리
    private static final Set<String> sessions = ConcurrentHashMap.newKeySet();
    private static List<TableDto> serverTables = new Vector<>();

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
