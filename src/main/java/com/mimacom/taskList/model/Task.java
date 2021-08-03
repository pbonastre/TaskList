package com.mimacom.taskList.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Generated
@NoArgsConstructor
@Table(name = "TASKS")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes="unique identifier of the task", example="1", required = true, position = 0)
    protected long id;

    @Column(nullable = false)
    @NotNull
    protected String title;

    @Column(nullable = false)
    @NotNull
    protected String description;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyyMMdd")
    protected Date finish;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task(String title, String description, Date finish){
        this.title = title;
        this.description = description;
        this.finish = finish;
    }

    public Task(Long id,String title, String description, Date finish){
        this.id = id;
        this.title = title;
        this.description = description;
        this.finish = finish;
    }

}
