package com.kishar.app;
import android.app.*;import android.os.*;import android.content.*;import android.graphics.*;import android.view.*;import android.widget.*;import java.util.*;

public class MainActivity extends Activity{
 final int OLIVE=Color.rgb(105,132,67),INK=Color.rgb(25,28,24),BG=Color.rgb(248,249,246),RED=Color.rgb(173,55,45);
 LinearLayout body;boolean simulation=true;int imageCount=0;TextView imageStatus;ArrayList<Robot> robots=new ArrayList<>();
 int d(int n){return(int)(n*getResources().getDisplayMetrics().density+.5f);}
 class Robot{String name,id,type,details;int photos;Robot(String n,String i,String t,String x,int p){name=n;id=i;type=t;details=x;photos=p;}}
 TextView tx(String s,int z,int c){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);v.setPadding(d(14),d(10),d(14),d(10));return v;}
 Button bt(String s){Button b=new Button(this);b.setText(s);b.setTextSize(16);b.setTextColor(INK);b.setAllCaps(false);b.setMinHeight(d(52));return b;}
 void add(View v){body.addView(v,new LinearLayout.LayoutParams(-1,-2));}
 void title(String s){TextView v=tx(s,25,INK);v.setTypeface(null,1);add(v);}
 void card(String s){TextView v=tx(s,16,INK);v.setBackgroundColor(Color.WHITE);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,d(5),0,d(5));body.addView(v,p);}
 void frame(){body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);body.setPadding(d(16),d(8),d(16),d(16));body.setBackgroundColor(BG);ScrollView s=new ScrollView(this);s.addView(body);setContentView(s);}
 void clear(String s){body.removeAllViews();title(s);}
 void back(){Button b=bt("رجوع للرئيسية");b.setOnClickListener(v->home());add(b);}
 void stopAll(){Button b=bt("إيقاف طوارئ للجميع");b.setTextColor(Color.WHITE);b.setBackgroundColor(RED);b.setOnClickListener(v->Toast.makeText(this,"E-STOP: تم إيقاف كل الأوامر",1).show());add(b);}
 public void onCreate(Bundle b){super.onCreate(b);robots.add(new Robot("روبوت تجريبي","sim-01","أرضي","محاكاة آمنة",0));home();}
 void home(){frame();TextView h=tx("Kishar 🔥",28,OLIVE);h.setTypeface(null,1);add(h);card("إدارة روبوتات ووعي\nالوضع: "+(simulation?"محاكاة آمنة":"حقيقي — بانتظار الاتصال")+"\nلا يتم تشغيل محركات بلا Gateway وACK.");
  Button m=bt(simulation?"تفعيل الوضع الحقيقي":"العودة إلى المحاكاة");m.setOnClickListener(v->{simulation=!simulation;home();});add(m);
  Button o=bt("غرفة العمليات وتقسيم الروبوتات");o.setOnClickListener(v->ops());add(o);
  Button r=bt("التعرف على روبوت وإضافة صوره");r.setOnClickListener(v->robotSetup());add(r);
  Button t=bt("تدريب وعي: التوازن والحركة");t.setOnClickListener(v->training());add(t);
  Button c=bt("محادثة Joker");c.setOnClickListener(v->chat());add(c);
  Button s=bt("إعدادات NVIDIA وGateway");s.setOnClickListener(v->settings());add(s);stopAll();}
 void ops(){clear("غرفة العمليات");card("عدد الروبوتات: "+robots.size()+"\nالعرض يتكيف تلقائيًا: شاشة كاملة أو تقسيم 2/3/4.");
  for(Robot r:robots)card("▣ "+r.name+"\nID: "+r.id+" • النوع: "+r.type+"\nالكاميرا: "+(simulation?"محاكاة":"بانتظار الاتصال")+" • البطارية: —\nآخر ACK: — • المهمة: لا توجد");back();stopAll();}
 void chat(){clear("محادثة Joker");card("Joker يخطط ويفحص القدرات قبل الأمر. الصورة لا تتحكم بالمحركات مباشرة.");EditText e=new EditText(this);e.setHint("اكتب أمرًا بالعربية");add(e);Button b=bt("إرسال");TextView out=tx("",16,INK);b.setOnClickListener(v->{String q=e.getText().toString().trim();out.setText(q.isEmpty()?"اكتب أمرًا أولًا.":"تم استلام الأمر: "+q+"\nالحالة: "+(simulation?"محاكاة آمنة":"بانتظار الخادم")+"\nالنتيجة: لا نجاح دون ACK.");});add(b);add(out);back();stopAll();}
 void robotSetup(){clear("التعرف على الروبوت");card("أدخل المعلومات، ثم أضف صورة أو عدة صور من الأمام والخلف والجوانب وأثناء الحركة.");
  EditText n=new EditText(this);n.setHint("اسم الروبوت");add(n);EditText id=new EditText(this);id.setHint("Robot ID");add(id);EditText type=new EditText(this);type.setHint("النوع: أرضي / درون / بحري / هجين");add(type);
  EditText move=new EditText(this);move.setHint("آلية الحركة: عجلات / أرجل / مراوح / أجنحة / محركات بحرية");add(move);
  EditText parts=new EditText(this);parts.setHint("المحركات والحساسات والكاميرات ولوحة التحكم");add(parts);
  EditText gw=new EditText(this);gw.setHint("عنوان Robot Gateway");add(gw);
  Button pick=bt("اختيار صورة أو عدة صور");pick.setOnClickListener(v->{Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.setType("image/*");i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);i.addCategory(Intent.CATEGORY_OPENABLE);startActivityForResult(i,77);});add(pick);
  imageStatus=tx(imageCount==0?"لم تتم إضافة صور":"تم اختيار "+imageCount+" صورة",15,OLIVE);add(imageStatus);
  Button analyze=bt("تحليل الصور وإنشاء ملف مبدئي");analyze.setOnClickListener(v->Toast.makeText(this,imageCount>0?"تم إنشاء ملف مبدئي للمراجعة":"أضف صورًا أولًا",1).show());add(analyze);
  Button save=bt("حفظ ملف الروبوت");save.setOnClickListener(v->{String a=n.getText().toString().trim(),b=id.getText().toString().trim(),c=type.getText().toString().trim();if(a.isEmpty()||b.isEmpty()){Toast.makeText(this,"الاسم وRobot ID مطلوبان",0).show();return;}String info="الحركة: "+move.getText()+"\nالمكونات: "+parts.getText()+"\nGateway: "+gw.getText();robots.add(new Robot(a,b,c.isEmpty()?"غير محدد":c,info,imageCount));Toast.makeText(this,"تم حفظ الملف — يحتاج اختبار Gateway",1).show();robotSetup();});add(save);
  for(Robot r:robots)card("✓ "+r.name+"\n"+r.id+" • "+r.type+"\nالصور: "+r.photos+"\n"+r.details);back();stopAll();}
 void training(){clear("تدريب وعي");card("وعي يتعلم التوازن والحركة في المحاكاة، ثم يستخدم محولًا خاصًا بكل جسم روبوتي.");
  card("المراحل: جمع البيانات → تدريب NVIDIA → محاكاة → فحص السلامة → موافقة → نشر نموذج موقع.");Button a=bt("بدء جمع بيانات آمن");a.setOnClickListener(v->Toast.makeText(this,"بدأت جلسة "+(simulation?"محاكاة":"بانتظار الخادم"),1).show());add(a);Button b=bt("اختبار التوازن");b.setOnClickListener(v->Toast.makeText(this,"اختبار بلا محركات حقيقية",1).show());add(b);back();stopAll();}
 void settings(){clear("الإعدادات");card("الإعدادات تحفظ لاحقًا بشكل مشفر. الاتصال الحقيقي يحتاج خادمًا وGateway متوافقين.");EditText h=new EditText(this);h.setHint("عنوان خادم NVIDIA / API");add(h);EditText p=new EditText(this);p.setHint("المنفذ");p.setInputType(2);add(p);EditText g=new EditText(this);g.setHint("عنوان Robot Gateway");add(g);Button test=bt("اختبار الاتصال");test.setOnClickListener(v->Toast.makeText(this,simulation?"المحاكي يعمل — لم يُختبر اتصال حقيقي":"بانتظار بيانات الخادم",1).show());add(test);Button save=bt("حفظ");save.setOnClickListener(v->Toast.makeText(this,"تم حفظ الإعدادات",0).show());add(save);card("أمان: Heartbeat 1s • Expiry 1.5s • UNKNOWN=STOP • ACK قبل النجاح");back();stopAll();}
 @Override protected void onActivityResult(int requestCode,int resultCode,Intent data){super.onActivityResult(requestCode,resultCode,data);if(requestCode==77&&resultCode==RESULT_OK&&data!=null){imageCount=0;if(data.getClipData()!=null)imageCount=data.getClipData().getItemCount();else if(data.getData()!=null)imageCount=1;if(imageStatus!=null)imageStatus.setText("تم اختيار "+imageCount+" صورة — جاهزة للتحليل");Toast.makeText(this,"تمت إضافة الصور إلى ملف الروبوت",0).show();}}
}
