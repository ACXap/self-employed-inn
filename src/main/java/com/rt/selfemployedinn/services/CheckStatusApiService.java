package com.rt.selfemployedinn.services;

import com.rt.selfemployedinn.services.data.CheckedInn;
import com.rt.selfemployedinn.services.data.Status;
import com.rt.selfemployedinn.services.data.StatusType;
import com.rt.selfemployedinn.services.data.TaskCheckInn;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CheckStatusApiService {
    private final CheckStatusService service;
    private final Map<UUID, TaskCheckInn> mapTask = new HashMap<>();

    @Value("${max.queue.check.inn}")
    private int maxQueueCheckInn = 10;

    @Value("${pause.request}")
    private int pauseRequest = 30;

    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(maxQueueCheckInn);
    private final ExecutorService executor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, queue);

    public String addTask(List<String> collectionInn) {
        UUID uuid = UUID.randomUUID();
        TaskCheckInn task = new TaskCheckInn(uuid, collectionInn);
        mapTask.put(uuid, task);
        executor.execute(() -> process(collectionInn, task));
        return uuid.toString();
    }

    public List<CheckedInn> getResultTask(String taskId) throws Exception {
        TaskCheckInn task = getTask(taskId);

        if (task.getStatus().getStatus().equals(StatusType.COMPLETED)) {
            return task.getCheckedInCollection();
        }

        throw new Exception("Task not completed");
    }

    public Status getStatusTask(String taskId) throws Exception {
        TaskCheckInn task = getTask(taskId);
        return task.getStatus();
    }

    //region PrivateMethod
    private void process(List<String> collectionInn, TaskCheckInn task) {
        List<CheckedInn> result = new ArrayList<>();

        task.startTask();

        for (String inn : collectionInn) {
            result.add(service.checkInn(inn));
            pauseBetweenRequest();
        }

        task.stopTask(result);
    }

    private void pauseBetweenRequest(){
        try {
            TimeUnit.SECONDS.sleep(pauseRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void end() {
        executor.shutdownNow();
    }

    private UUID getId(String taskId) throws Exception {
        try {
            return UUID.fromString(taskId);
        } catch (IllegalArgumentException e) {
            throw new Exception("Id not correct");
        }
    }

    private TaskCheckInn getTask(String id) throws Exception {
        UUID uuid = getId(id);
        boolean isContains = mapTask.containsKey(uuid);

        if (!isContains) throw new Exception("Id not found");

        return mapTask.get(uuid);
    }
    //endregion PrivateMethod
}