package com.example.tutorial.Admin.StatusLaporan;

public class UpdateStatusRequest {
    public int status;

    public UpdateStatusRequest(int status) {
        this.status = status;
    }

    public int getStatus(){return status;}

    public void setStatus(int status){this.status = status;}
}
