package com.ruoyi.project.uppicture.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.sql.Timestamp;

public class Bimenclosure extends BaseEntity {

    private Long id;
    private Double x_coordinates;
    private Double y_coordinates;
    private Double h_coordinates;
    private String pic_url;
    private Timestamp photo_time;
    private Timestamp up_time;
    private String problem_type;
    private Long user_id;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getX_coordinates() {
        return x_coordinates;
    }

    public void setX_coordinates(Double x_coordinates) {
        this.x_coordinates = x_coordinates;
    }

    public Double getY_coordinates() {
        return y_coordinates;
    }

    public void setY_coordinates(Double y_coordinates) {
        this.y_coordinates = y_coordinates;
    }

    public Double getH_coordinates() {
        return h_coordinates;
    }

    public void setH_coordinates(Double h_coordinates) {
        this.h_coordinates = h_coordinates;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
    public Timestamp getPhoto_time() {
        return photo_time;
    }

    public void setPhoto_time(Timestamp photo_time) {
        this.photo_time = photo_time;
    }

    public Timestamp getUp_time() {
        return up_time;
    }

    public void setUp_time(Timestamp up_time) {
        this.up_time = up_time;
    }

    public String getProblem_type() {
        return problem_type;
    }

    public void setProblem_type(String problem_type) {
        this.problem_type = problem_type;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

}
