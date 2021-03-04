package com.rt.selfemployedinn.services.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.rt.selfemployedinn.services.data.Status.Error;
import static com.rt.selfemployedinn.services.data.Status.Start;

@RequiredArgsConstructor
@Getter
public class TaskCheckInn {
    private final UUID id;
    private final Date dataCreate = new Date();
    private final List<String> requestInnCollection;

    private Status status = Status.Stop("Задача еще не запущена");
    private Date dateStart;
    private Date dateStop;
    private List<CheckedInn> checkedInCollection;

    public void startTask() {
        status = Start("Задача запущена");
        dateStart = new Date();
    }

    public void errorTask(String message, List<CheckedInn> collection) {
        checkedInCollection = collection;
        status = Error(message);
        dateStop = new Date();
    }

    public void stopTask(List<CheckedInn> collection) {
        checkedInCollection = collection;
        status = new Status(StatusType.COMPLETED, new Date(), "Задача завершена");
        dateStop = new Date();
    }
}