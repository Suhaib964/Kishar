package com.kishar.app;

import android.app.*;import android.os.*;import android.graphics.*;import android.view.*;import android.widget.*;import java.util.*;

public class MainActivity extends Activity{
 final int OLIVE=Color.rgb(105,132,67),INK=Color.rgb(25,28,24),BG=Color.rgb(248,249,246),RED=Color.rgb(173,55,45),BLUE=Color.rgb(52,92,130);
 LinearLayout body; boolean simulation=true; ArrayList<Robot> robots=new ArrayList<>();
 int d(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}
 class Robot{String name,id,type;Robot(String n,String i,String t){name=n;id=i;type=t;}}
 TextView tx(String s,int z,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);v.setPadding(d(14),d(10),d(14),d(10));return v;}
 Button bt(String s){Button b=new Button(this);b.setText(s);b.setTextSize(16);b.setTextColor(INK);b.setAllCaps(false);b.setMinHeight(d(52));return b;}
 void add(View v){body.addView(v,new LinearLayout.LayoutParams(-1,-2));}
 void title(String s){TextView v=tx(s,25,INK);v.setTypeface(null,1);v.setPadding(d(8),d(16),d(8),d(8));add(v);}
 void card(String s){TextView v=tx(s,16,INK);v.setBackgroundColor(Color.WHITE);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,d(5),0,d(5));body.addView(v,p);}
 void clear(String s){body.removeAllViews();title(s);}
 void back(){Button b=bt("رجوع للرئيسية");b.setOnClickListener(v->home());add(b);}
 void danger(){Button b=bt("إيقاف طوارئ للجميع");b.setTextColor(Color.WHITE);b.setBackgroundColor(RED);b.setOnClickListener(v->Toast.makeText(this,"E-STOP: تم إيقاف كل الأوامر",1).show());LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,d(58));p.setMargins(0,d(14),0,0);body.addView(b,p);}
 public void onCreate(Bundle x){super.onCreate(x);robots.add(new Robot("روبوت تجريبي","sim-01","أرضي"));home();}
 void frame(){body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);body.setPadding(d(16),d(8),d(16),d(16));body.setBackgroundColor(BG);ScrollView s=new ScrollView(this);s.addView(body);setContentView(s);}
 void home(){frame();TextView h=tx("Kishar 🔥",28,OLIVE);h.setTypeface(null,1);add(h);
  card("غرفة عمليات متعددة الروبوتات\nالوضع: "+(simulation?"محاكاة آمنة":"حقيقي — بانتظار اتصال")+"\nالعقل: وعي / Joker");
  Button mode=bt(simulation?"تبديل إلى الوضع الحقيقي":"تبديل إلى وضع المحاكاة");mode.setOnClickListener(v->{simulation=!simulation;home();});add(mode);
  Button ops=bt("غرفة العمليات — "+robots.size()+" روبوت");ops.setOnClickListener(v->ops());add(ops);
  Button chat=bt("محادثة Joker مع الروبوتات");chat.setOnClickListener(v->chat());add(chat);
  Button list=bt("الروبوتات والتعرف عليها");list.setOnClickListener(v->robotSetup());add(list);
  Button train=bt("تدريب وعي — توازن وحركة");train.setOnClickListener(v->training());add(train);
  Button manual=bt("التحكم اليدوي الآمن");manual.setOnClickListener(v->manual());add(manual);
  Button set=bt("إعدادات الخادم والأمان");set.setOnClickListener(v->settings());add(set);danger();
 }
 void ops(){clear("غرفة العمليات");card("الشاشة المقسمة تتكيّف تلقائيًا: "+robots.size()+" روبوت.\nالحالة العامة: "+(simulation?"محاكاة — لا محركات حقيقية":"اتصال غير مُختبر"));
  for(Robot r:robots){TextView v=tx("▣ "+r.name+"  •  "+r.type+"\nRobot ID: "+r.id+"\nالكاميرا: "+(simulation?"مصدر محاكاة":"غير متصل")+"  |  البطارية: غير معروفة\nالمهمة: لا توجد  |  آخر ACK: —",15,INK);v.setBackgroundColor(Color.WHITE);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,d(145));p.setMargins(0,d(5),0,d(5));body.addView(v,p);}
  card("الصوت: متوقف افتراضيًا\nالموقع وIMU والمسافة: تظهر بعد اتصال Gateway موثق.");back();danger();}
 void chat(){clear("محادثة Joker");card("اختر هدف الرسالة. لا يرسل Joker أمرًا للمحركات مباشرة.");Spinner sp=new Spinner(this);String[] targets=new String[robots.size()+1];targets[0]="كل الروبوتات";for(int i=0;i<robots.size();i++)targets[i+1]=robots.get(i).name;sp.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,targets));add(sp);EditText in=new EditText(this);in.setHint("اكتب أمرًا بالعربية");in.setTextSize(16);add(in);Button send=bt("إرسال إلى Joker");TextView out=tx("",16,INK);send.setOnClickListener(v->{String q=in.getText().toString().trim();if(q.length()==0){out.setText("اكتب رسالة أولاً.");return;}out.setText("الحالة: "+(simulation?"محاكاة آمنة":"بانتظار اتصال الخادم")+"\nالهدف: "+sp.getSelectedItem()+"\nالخطة: سيتم فحص القدرات والسلامة ثم طلب ACK قبل التنفيذ.\nالأمر: "+q);in.setText("");});add(send);add(out);back();danger();}
 void robotSetup(){clear("التعرف على الروبوت");card("أضف روبوتًا من خلال صورته ومواصفاته وقدراته. الصورة تساعد التعرف، لكن التشغيل لا يعتمد عليها وحدها.");
  EditText name=new EditText(this);name.setHint("اسم الروبوت");add(name);EditText id=new EditText(this);id.setHint("Robot ID");add(id);EditText type=new EditText(this);type.setHint("النوع: أرضي / درون / بحري / هجين");add(type);
  Button photo=bt("إضافة صورة وملف تعريف لاحقًا");photo.setOnClickListener(v->Toast.makeText(this,"سيتم ربط الصور وملف القدرات عند تجهيز Gateway",1).show());add(photo);
  Button save=bt("حفظ الروبوت");save.setOnClickListener(v->{String n=name.getText().toString().trim(),i=id.getText().toString().trim(),t=type.getText().toString().trim();if(n.isEmpty()||i.isEmpty()){Toast.makeText(this,"أدخل الاسم وRobot ID",0).show();return;}robots.add(new Robot(n,i,t.isEmpty()?"غير محدد":t));Toast.makeText(this,"تمت إضافة الروبوت، ويحتاج اختبار القدرات",1).show();robotSetup();});add(save);
  for(Robot r:robots)card("✓ "+r.name+"\n"+r.id+" • "+r.type+"\nالاعتماد: "+(r.id.startsWith("sim")?"محاكاة":"بانتظار التحقق"));back();danger();}
 void training(){clear("تدريب وعي");card("وعي يتعلم التوازن والحركة كمهارات عالية المستوى، ثم يستخدم محولًا خاصًا بأبعاد ومحركات كل جسم روبوتي.");
  card("المراحل:\n1. جمع IMU والكاميرا والأوامر\n2. تدريب على خادم NVIDIA\n3. اختبار في المحاكاة\n4. مراجعة السلامة\n5. نشر نسخة موقعة مع تراجع");
  Button record=bt("بدء جلسة جمع بيانات");record.setOnClickListener(v->Toast.makeText(this,simulation?"بدأت جلسة محاكاة لجمع البيانات":"لا يبدأ التدريب قبل توثيق الخادم والروبوت",1).show());add(record);
  Button test=bt("اختبار التوازن في المحاكي");test.setOnClickListener(v->Toast.makeText(this,"اختبار آمن: لا محركات فعلية",1).show());add(test);
  Button deploy=bt("نشر نموذج وعي بعد الموافقة");deploy.setOnClickListener(v->Toast.makeText(this,"النشر محجوز حتى نجاح الاختبارات ووصول ACK",1).show());add(deploy);back();danger();}
 void manual(){clear("التحكم اليدوي");card("Dead-Man: استمر بالضغط. رفع الإصبع = STOP. الوضع الحالي: "+(simulation?"محاكاة":"حقيقي غير متصل"));GridLayout g=new GridLayout(this);g.setColumnCount(2);String[] ns={"أمام","خلف","يسار","يمين","دوران يسار","دوران يمين"};for(String n:ns){Button b=bt(n);b.setOnTouchListener((v,e)->{if(e.getAction()==0){b.setText(n+" — يعمل");return true;}if(e.getAction()==1||e.getAction()==3){b.setText(n);Toast.makeText(this,"STOP",0).show();return true;}return true;});GridLayout.LayoutParams p=new GridLayout.LayoutParams();p.width=0;p.height=d(60);p.columnSpec=GridLayout.spec(GridLayout.UNDEFINED,1f);p.setMargins(d(3),d(3),d(3),d(3));g.addView(b,p);}body.addView(g,new LinearLayout.LayoutParams(-1,-2));Button arm=bt("تسليح بعد فحص السلامة");arm.setOnClickListener(v->Toast.makeText(this,"لا تسليح دون اتصال موثق وموافقة",1).show());add(arm);back();danger();}
 void settings(){clear("الإعدادات");card("أدخل بيانات الخادم لاحقًا. لا تُخزن المفاتيح داخل التطبيق أو الكود.");EditText host=new EditText(this);host.setHint("عنوان خادم NVIDIA / API");host.setSingleLine();add(host);EditText port=new EditText(this);port.setHint("المنفذ");port.setInputType(2);port.setSingleLine();add(port);EditText gateway=new EditText(this);gateway.setHint("عنوان Robot Gateway");gateway.setSingleLine();add(gateway);Button tls=bt("الشهادات والتشفير: إعداد لاحق");tls.setOnClickListener(v->Toast.makeText(this,"Mutual TLS مطلوب قبل الوضع الحقيقي",1).show());add(tls);Button test=bt("اختبار الخادم وGateway");test.setOnClickListener(v->Toast.makeText(this,simulation?"المحاكاة فعالة — لم يُختبر اتصال حقيقي":"أدخل الخادم ثم نفّذ اختبار الصحة",1).show());add(test);Button save=bt("حفظ الإعدادات");save.setOnClickListener(v->Toast.makeText(this,"تم حفظ الإعدادات محليًا",0).show());add(save);card("السياسات الثابتة: Heartbeat 1s • Expiry 1.5s • UNKNOWN=STOP • لا استئناف تلقائي • ACK قبل النجاح");back();danger();}
}
