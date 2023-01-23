package com.fiteprojects.fitegis.Utils.Models;

import java.util.List;

public class ResponseObject<Model> {

    String Message;
    String Error;
    Model Model;
    List<Model> List;

    public ResponseObject(String message) {
        this.Message = message;
    }

    public ResponseObject(String message, String error) {
        this.Message = message;
        this.Error = error;
    }

    public ResponseObject(String message, List<Model> list) {
        this.Message = message;
        this.List = list;
    }

    public ResponseObject(String message, Model model) {
        this.Message = message;
        this.Model = model;
    }

    public ResponseObject(String message, Model model, List<Model> list) {
        this.Message = message;
        this.Model = model;
        this.List = list;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public Model getModel() {
        return Model;
    }

    public void setModel(Model model) {
        this.Model = model;
    }

    public List<Model> getList() {
        return List;
    }

    public void setList(List<Model> list) {
        this.List = list;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        this.Error = error;
    }

    @Override
    public String toString() {
        return
                "{\n\"message\" :\"" + Message +
                        "\",\n\"error\" :\"" + Error +
                        "\",\n\"model\" :\"" + Model +
                        "\",\n\"list\" :\"" + List +
                        "\"\n}";
    }
}
