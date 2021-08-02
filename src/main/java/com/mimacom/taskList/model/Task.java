package com.mimacom.taskList.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

}
