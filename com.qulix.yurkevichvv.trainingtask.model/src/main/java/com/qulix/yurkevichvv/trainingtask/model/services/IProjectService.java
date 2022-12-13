package com.qulix.yurkevichvv.trainingtask.model.services;

import java.io.Serializable;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

/**
 * Методы для работы с Project.
 *
 * @author Q-YVV
 */
public interface IProjectService extends IService<Project>, Serializable {

    /**
     * Возвращает список задач проекта.
     *
     * @param project проект
     * @return список задач
     */
    List<Task> getProjectsTasks(Project project) throws ServiceException;

    /**
     * Удаляет задачу из проекта.
     *
     * @param project проект
     * @param task задача
     */
    void deleteTask(Project project, Task task) throws ServiceException;

    /**
     * Добавляет задачу в проект.
     *
     * @param project проект.
     * @param task задача.
     */
    void addTask(Project project, Task task) throws ServiceException;

    /**
     * Редактирует задачу в проекте.
     *
     * @param project проект
     * @param index номер задачи в списке
     * @param task задача
     */
    void updateTask(Project project, Integer index, Task task) throws ServiceException;
}
