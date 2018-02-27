package Model;

import java.io.Serializable;

public class SinhVien implements Serializable {
    private static int Msv = 0;
    private int msv;
    private String tenSV;
    private String email;

    public SinhVien() {
    }
    public SinhVien(String tenSV, String email) {
        this.msv = this.Msv++;
        this.tenSV = tenSV;
        this.email = email;
    }

    public int getMsv() {
        return msv;
    }

    public void setMsv(int msv) {
        this.msv = msv;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SinhVien)) return false;

        SinhVien sinhVien = (SinhVien) o;

        return getMsv() == sinhVien.getMsv();
    }

    @Override
    public int hashCode() {
        return getMsv();
    }
}
