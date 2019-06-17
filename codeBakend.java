import java.util.concurrent.BlockingQueue; 
import java.util.concurrent.ArrayBlockingQueue; 
import java.util.concurrent.LinkedBlockingQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
import java.util.Random;


/*
 This class is for CRM call.
 At the CRM object we have threadPry -> prayority & data -> String of the call  
 */
class DataCRM {
    private int threadPry;
    private String data;
    
    DataCRM(int threadPry,String data){
        this.threadPry = threadPry;
        this.data = data;
    }
    
    String getdata(){
        return this.data;
    }
    int getprayority(){
        return this.threadPry;
    }
}



/*  This class is for CRM thread.
    foreach CRM call I create new thread. the thread send the CRM call to 
    the main thread by massage.put
*/
class RunnableDemo implements Runnable {
   private Thread t;
   private String threadName;
   private BlockingQueue<DataCRM> massage;
   private int threadPry;

   
   RunnableDemo( int p, String name, BlockingQueue<DataCRM> msg) {
      this.threadName = name;
      this.massage =msg;
      this.threadPry = p;
   }
   
   public void run() {
       try{
            DataCRM data = new DataCRM(threadPry,threadName);
            this.massage.put(data);
       }
       catch(InterruptedException e){
           System.out.println("e " +  e );
       }
   }
   
   public void start () {
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}


/*  This class is for Main thread.
    the main thread get the massage from queue and write them according thire prayorits.
    at the end -> print the log.
*/
class RunnableMain implements Runnable {
   private int MAX_CRM;
   private Thread t;
   private String threadName;
   private BlockingQueue<DataCRM> massage;
   private List<DataCRM> arrayList =  new ArrayList<DataCRM>(); ;


   RunnableMain( String name, BlockingQueue<DataCRM> msg, int max_crm) {
       this.threadName = name;
       this.massage =msg;
       this.MAX_CRM = max_crm;
   }
   
     void insertSorted(int n, DataCRM key, int pratority) 
    { 
        if(n == 0)
        {
            this.arrayList.add(key);
            return;
        }
        int i; 
        //sharch the right index to add the new CRM elemnt
        for (i=n-1; ( i >=0  && this.arrayList.get(i).getprayority() > pratority); i--) ;
        i++;
        this.arrayList.add(i,key);
        
        //Print for test
        if(this.arrayList.size() == this.MAX_CRM){
                    System.out.println("take ");
                    for(int j=0; j<MAX_CRM; j++){
                        System.out.println("prayority: "+this.arrayList.get(j).getprayority()+", data: "+this.arrayList.get(j).getdata());
                    }
                }
        return; 
    }   



   public void run() {
      while(true){
            try{
                DataCRM takemassage = this.massage.take();
                insertSorted(this.arrayList.size(),takemassage,takemassage.getprayority());
            }
           catch(InterruptedException e){
               System.out.println("e " +  e );
           }
      }
   }
   
   public void start () {
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}

public class TestThread {
   static int MAX_CRM = 100;

   public static void main(String args[]) {
    BlockingQueue<DataCRM> massage = new LinkedBlockingQueue<DataCRM>();
    RunnableMain R1 = new RunnableMain("Thread-main",massage,MAX_CRM);
    R1.start();
    
    
    for(int i=0; i<MAX_CRM; i++){
        Random rand = new Random();
        // Obtain a number between [0 - 4] for prayority
        int n = rand.nextInt(5);
        
        //demo the CRM call - for each CRM call we create a thred that exc SendCRM() function.
        RunnableDemo R2 = new RunnableDemo(n,"CRM-Massage-Number-"+i,massage);
        R2.start();
    }
    
   }  
   
}