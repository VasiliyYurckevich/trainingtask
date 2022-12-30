package com.qulix.yurkevichvv.trainingtask.model.temporary;

import java.io.Serializable;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ServiceException;

/**
 * Обобщает основные методы для редактирования проекта и связанных с ним данных.
 *
 * @author Q-YVV
 */
public interface IProjectTemporaryService extends Serializable {

    /**
     * Сохраняет проект и связанные задачи в БД.
     *
     * @param projectTemporaryData данные связанные с проектом
     */
    void save(ProjectTemporaryData projectTemporaryData) throws ServiceException;

    /**
     * Возвращает список задач проекта.
     *
     * @param id проект
     * @return список задач
     */
    List<Task> getProjectsTasks(Integer id) throws ServiceException;

    /**
     * Удаляет задачу из проекта.
     *
     * @param project проект
     * @param task задача
     */
    void deleteTask(ProjectTemporaryData project, Task task) throws ServiceException;

    /**
     * Добавляет задачу в проект.
     *
     * @param project проект.
     * @param task задача.
     */
    void addTask(ProjectTemporaryData project, Task task) throws ServiceException;

    /**
     * Редактирует задачу в проекте.
     *
     * @param project проект
     * @param index номер задачи в списке
     * @param task задача
     */
    void updateTask(ProjectTemporaryData project, Integer index, Task task) throws ServiceException;
}
