package com.kishar.app;

import android.app.*;import android.os.*;import android.graphics.*;import android.view.*;import android.widget.*;

public class MainActivity extends Activity{
 int olive=Color.rgb(105,132,67),ink=Color.rgb(25,28,24),bg=Color.rgb(248,249,246),red=Color.rgb(173,55,45); LinearLayout body;
 int d(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}
 TextView tx(String s,int z,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);v.setPadding(d(16),d(10),d(16),d(10));return v;}
 Button bt(String s){Button b=new Button(this);b.setText(s);b.setTextSize(16);b.setTextColor(ink);b.setAllCaps(false);b.setMinHeight(d(52));return b;}
 void add(View v){body.addView(v,new LinearLayout.LayoutParams(-1,-2));}
 void title(String s){TextView v=tx(s,25,ink);v.setTypeface(null,1);v.setPadding(d(8),d(16),d(8),d(8));add(v);}
 void card(String s){TextView v=tx(s,16,ink);v.setBackgroundColor(Color.WHITE);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,d(6),0,d(6));body.addView(v,p);}
 void back(){Button b=bt("رجوع للرئيسية");b.setOnClickListener(v->home());add(b);}
 void clear(String s){body.removeAllViews();title(s);}
 public void onCreate(Bundle b){super.onCreate(b);home();}
 void home(){body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);body.setPadding(d(16),d(8),d(16),d(16));body.setBackgroundColor(bg);ScrollView sc=new ScrollView(this);sc.addView(body);setContentView(sc);
  TextView br=tx("Kishar 🔥",28,olive);br.setTypeface(null,1);add(br);card("غرفة عمليات سهلة وبسيطة\nالوضع الحالي: محاكي آمن — لا يوجد روبوت فعلي متصل.");
  Button a=bt("محادثة Joker");a.setOnClickListener(v->chat());add(a);Button c=bt("غرفة العمليات");c.setOnClickListener(v->ops());add(c);Button m=bt("التحكم اليدوي");m.setOnClickListener(v->manual());add(m);Button s=bt("الإعدادات");s.setOnClickListener(v->settings());add(s);emergency();}
 void chat(){clear("محادثة Joker");card("الوضع التجريبي: لا يتم إرسال أوامر إلى روبوت حقيقي.");EditText in=new EditText(this);in.setHint("اكتب أمراً بالعربية");in.setTextSize(16);add(in);Button send=bt("إرسال");TextView out=tx("",16,ink);send.setOnClickListener(v->{String q=in.getText().toString().trim();out.setText(q.isEmpty()?"اكتب رسالة أولاً.":"حالة Joker: ينتظر التنفيذ\nاستلمت: "+q+"\nالمحاكي الآمن لا يرسل أوامر فعلية.");in.setText("");});add(send);add(out);back();emergency();}
 void ops(){clear("غرفة العمليات");card("الحالة: غير متصل — محاكي آمن");card("البطارية: غير متاحة\nالاتصال: غير متاح\nالكاميرا: غير متصلة\nالمهمة: لا توجد");card("لا يتم حفظ الفيديو أو الصوت افتراضياً.");back();emergency();}
 void manual(){clear("التحكم اليدوي");card("اضغط باستمرار للحركة. عند رفع الإصبع يتوقف الأمر فوراً.");GridLayout g=new GridLayout(this);g.setColumnCount(2);String[] ns={"أمام","خلف","يسار","يمين","دوران يسار","دوران يمين"};for(String n:ns){Button b=bt(n);b.setOnTouchListener((v,e)->{if(e.getAction()==0){b.setText(n+" — يعمل");return true;}if(e.getAction()==1||e.getAction()==3){b.setText(n);Toast.makeText(this,"STOP — المحاكي الآمن",0).show();return true;}return true;});GridLayout.LayoutParams p=new GridLayout.LayoutParams();p.width=0;p.height=d(60);p.columnSpec=GridLayout.spec(GridLayout.UNDEFINED,1f);p.setMargins(d(3),d(3),d(3),d(3));g.addView(b,p);}body.addView(g,new LinearLayout.LayoutParams(-1,-2));Button stop=bt("توقف");stop.setTextColor(Color.WHITE);stop.setBackgroundColor(red);stop.setOnClickListener(v->Toast.makeText(this,"تم التوقف",0).show());add(stop);back();emergency();}
 void settings(){clear("الإعدادات");card("إعدادات قليلة وواضحة. الاتصال بالخادم غير مفعل في النسخة التجريبية.");EditText server=new EditText(this);server.setHint("عنوان خادم NVIDIA");server.setSingleLine();add(server);EditText port=new EditText(this);port.setHint("المنفذ");port.setInputType(2);port.setSingleLine();add(port);Button save=bt("حفظ الإعدادات");save.setOnClickListener(v->Toast.makeText(this,"تم الحفظ على الجهاز",0).show());add(save);Button test=bt("اختبار الاتصال");test.setOnClickListener(v->Toast.makeText(this,"لا يوجد خادم متصل — محاكي آمن",1).show());add(test);back();emergency();}
 void emergency(){Button e=bt("إيقاف طوارئ");e.setTextColor(Color.WHITE);e.setBackgroundColor(red);e.setOnClickListener(v->Toast.makeText(this,"تم إرسال إيقاف طوارئ — لا يوجد روبوت متصل",1).show());LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,d(58));p.setMargins(0,d(14),0,0);body.addView(e,p);}
}
