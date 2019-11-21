package com.okta.jerseyrest.resource;

import com.okta.jerseyrest.model.Task;
import com.okta.jerseyrest.request.TaskRequest;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@Path("/tasks")
public class TaskResource {

    private List<Task> tasks = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasks() {
        return tasks;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createTask(TaskRequest request) {
        UUID taskId = UUID.randomUUID();
        tasks.add(new Task(taskId, request.getDescription()));

        return taskId.toString();
    }

    @PUT
    @Path("/{taskId}")
    public Response updateTask(@PathParam("taskId") UUID taskId, TaskRequest request) {
        Optional<Task> taskToUpdate = tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst();

        if (!taskToUpdate.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Task task = taskToUpdate.get();
        task.setDescription(request.getDescription());

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{taskId}")
    public Response deleteTask(@PathParam("taskId") UUID taskId) {
        tasks = tasks.stream()
                .filter(task -> !task.getId().equals(taskId))
                .collect(Collectors.toList());

        return Response.noContent().build();
    }

}
