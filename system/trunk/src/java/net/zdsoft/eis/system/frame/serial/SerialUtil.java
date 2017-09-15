package net.zdsoft.eis.system.frame.serial;

public class SerialUtil {

	private static final String	table = "T3WZSCXGHLQ5VFIMA98K2RU71PEJ64YN";
	private static final int DELTA = 0x9e3779b9; /* sqr(5)-1 * 2^31 */
	private static final int ROUNDS = 32;
	private SerialField sField = new SerialField();

	public SerialField getSerial(){
		return sField;
	}
    public String getModule0(String path, String serial, int type){
    	return "";
    }

    public int VerifySerial0(String s, String s1, String s2, String s3){
    	return 1;
    }

    public String GetExpireDate0(String s){
    	return "2010/12/30";
    }

    public int GetUserCount0(String s){
    	return 2000;
    }

    public String GetFuncSerial0(String s){
   	
    	return "11111111111111111111111111111111";
    }
    
    class SerialField{
    	public int usercount,year,month,day,ver,func;
    	public int[] add = {0,0,0};
		@Override
		public String toString() {
			String info = "UserCount:"+usercount;
			info +="  year:"+year; 
			info +="  month:"+month;
			info +="  day:"+day;
			info +="  ver:"+ver;
			info +="  func:"+func;
			info +="  add0:"+add[0];
			info +="  add1:"+add[1];
			info +="  add2:"+add[2];
			return info;
		}
    	
    }
    
    //演示教育局ANNH3-XK62Q-YCUTY-5ZWKW-4YIKL-PQR16-AAJS2	3.6标准版2008-12-31 4基本
    public Boolean fromSerial2( String serial)
    //, int &nUserCount, int &nYear, int &nMonth, int &nDay, int &nVer, int &nFunc, int nAdd[]
    {
    	byte[] code = new byte[35];
    	String realSerial = serial.replaceAll("-", "");
    	
    	//	error code -1
    	//	序列号长度错误
    	if(realSerial.length() != 35) return false;
    	
    	//	error code -1
    	//	序列号中出现非法字符
    	for(int i = 0;i < 35;i++){
    		code[i] = (byte)table.indexOf(realSerial.substring(i, i+1));
    		if(code[i]<0) return false;
    	}
    	

    	for(int i = 0; i < 100; i++ )
    		fromChaos( code );


    	int		nCount=0;
    	for(int  i = 0; i < 34; i++ )
    		nCount += code[i];
    	if( code[34] != nCount%32 )
    		return false;


    	int[] n = {0,0,0,0};
    	for(int i = 0; i < 6; i++ )
    	{
    		n[0] += code[   i]<<(5*i);
    		n[1] += code[6 +i]<<(5*i);
    		n[2] += code[12+i]<<(5*i);
    		n[3] += code[18+i]<<(5*i);
    	}
    	

    	n[0] += (code[24]&0x3)<<30;
    	code[24] >>= 2;
    	n[1] += code[24]<<30;
    	
    	n[2] += (code[25]&0x3)<<30;
    	code[25] >>= 2;
    	n[3] += code[25]<<30;

    	//		密钥修改
//    	word32		k[4] = { 0x2923ADF9, 0x78DE841, 0x3EC1B42F, 0x2A9FE8CF };
    	int[] k = { 0x29C6AEF9, 0x78DF861, 0x3ED1B82F, 0x2A9DB8CF };
    	cl_dec_block( k, n ,0);
    	cl_enc_block( k, n ,2);

    	
    	sField.func		= n[0]; //nFunc
    	sField.usercount	= n[1]&0x7FFF;//nUserCount
    	n[1] >>= 15;
    	sField.ver	    = n[1]&0x3FF;//nVer 
    	n[1] >>= 10;
    	sField.year		= n[1];//nYear

    	int		nLeft, nRight;
    	sField.month		= n[2]&0xF;//nMonth
    	n[2] >>= 4;
    	sField.day		= n[2]&0x1F;//nDay
    	n[2] >>= 5;
    	sField.add[0]		= n[2]&0x1FFFF;//nAdd[0]
    	n[2] >>= 17;
    	nLeft		= n[2];

    	nRight		= n[3]&0x7FF;
    	sField.add[1] = nLeft + (nRight<<6);//nAdd[1]
    	n[3] >>= 11;
    	sField.add[2] = n[3];//nAdd[2]

    	//	用户数量错误
    	if( sField.usercount < 0 ) //nUserCount
    		return false;

    	//	版本号错误
    	if( sField.ver < 0 ) //nVer
    		return false;

    	//	时间错误
    	if( sField.year < 0 || sField.month < 0 || sField.day < 0 ||
    		sField.year > 100 || sField.month > 12 || sField.day > 31 )
    		return false;

    	return true;
    }
    
    
    private void fromChaos( byte a[] )
    {
    	int		i, j;
    	for( i = 0; i < 5; i++ )
    		swap(a, 2+i*5, 30+i );

    	if( (a[32]%2) != 0 )
    	{
    		for( i = a[31]-1; i >= 0; i-- )
    		{
    			for( j = 29; j > 0; j-- )
    				swap(a, j-1, j+1 );
    		}
    	}	

    	for( i = a[30]-1; i >= 0; i-- )
    	{
    		for( j = 29; j > 0; j-- )
    			swap(a, j-1, j );
    	}
    }
    
    private void swap( byte a[],int index1, int index2 )
    {
    	byte	temp;
    	temp = a[index1];
    	a[index1] = a[index2];
    	a[index2] = temp;
    }

    
    private void tean(int k[], int v[], int n,int pos) {
    	  int y=v[pos], z=v[pos+1];
    	  int limit,sum=0;
    	  if(n>0) 
    	  { /* ENCRYPT */
    	    limit=DELTA*n;
    	    while(sum!=limit) 
    		{
    	      y+=((z<<4)^(z>>5)) + (z^sum) + k[sum&3];
    	      sum+=DELTA;
    	      z+=((y<<4)^(y>>5)) + (y^sum) + k[(sum>>11)&3];
    	    }
    	  } 
    	  else 
    	  { /* DECRYPT */
    	    sum=DELTA*(-n);
    	    while(sum != 0) 
    		{
    	      z-=((y<<4)^(y>>5)) + (y^sum) + k[(sum>>11)&3];
    	      sum-=DELTA;
    	      y-=((z<<4)^(z>>5)) + (z^sum) + k[sum&3];
    	    }
    	  }
    	  v[pos]=y; v[pos+1]=z;
    }
    
    private void cl_enc_block(int k[], int v[],int pos) 
    {
     tean(k,v,ROUNDS,pos);
    }
    
    private void cl_dec_block(int k[], int v[],int pos) 
    {
     tean(k,v,-ROUNDS,pos);
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String serial = "ANNH3-XK62Q-YCUTY-5ZWKW-4YIKL-PQR16-AAJS2";
    	
    	SerialUtil su = new SerialUtil();
    	
    	su.fromSerial2(serial);
    	System.out.print(su.getSerial().toString());

	}

}
