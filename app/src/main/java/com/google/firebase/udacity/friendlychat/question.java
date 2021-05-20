package com.google.firebase.udacity.friendlychat;

import android.content.Context;

public class question {
    String que,opa,opb,opc,opd,crctop;
    Context context;
    public question() {
    }
    public question(String a,String b,String c,String d,String e,String f){
        que=a;opa=b;opb=c;opc=d;opd=e;crctop=f;
    }
    public String getque(){return que;}
    public void setque(String h){que=h;}
    public String getopa(){return opa;}
    public void setopa(String h){opa=h;}
    public String getopb(){return opb;}
    public void setopb(String h){opb=h;}
    public String getopc(){return opc;}
    public void setopc(String h){opc=h;}
    public String getopd(){return opd;}
    public void setopd(String h){opd=h;}
    public String getcrctop(){return crctop;}
    public void setcrctop(String h){crctop=h;}
}
