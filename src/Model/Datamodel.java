package Model;

import java.io.Serializable;

public class Datamodel implements Serializable {
    private SinhVien sv;
    private String status;

    public Datamodel() {

    }

    public Datamodel(SinhVien sv, String status) {
        this.sv = sv;
        this.status = status;
    }

    public SinhVien getSv() {
        return sv;
    }

    public void setSv(SinhVien sv) {
        this.sv = sv;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
