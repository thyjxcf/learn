package net.zdsoft.leadin.util;

/*
 这是一个可以把给定密码转变成密码明文显示，也可以从密码明文还原
 你可以使用以下写法构造：
 PWD pwd=new PWD();
 pwd.setPassword("password");
 或
 PWD pwd=new PWD("password");
 
 然后调用方法pwd.encode()可以得到转变后的密码明文形式
 调用方法pwd.decode(String mPWD)可以从密码明文得到原密码
 */

public class PWD {
	public String pwdString; // string of the password
	public int pwdLength; // length of the password
	public byte[] pwdBytes; // byte array of the password
	public byte[] encodedBytes1, encodedBytes2;
	public int pos1, pos2;
	public int check;
	public byte[] encodedArray;
	public static final char[] CHS = { 'L', 'K', 'J', '4', 'D', 'G', 'F', 'V',
			'R', 'T', 'Y', 'B', 'N', 'U', 'P', 'W', '3', 'E', '5', 'H', 'M',
			'7', 'Q', '9', 'S', 'A', 'Z', 'X', '8', 'C', '6', '2' };

	/*
	 * You can use PWD(),then call setPassword(String s) param(s) The password
	 */
	public PWD() {
		pwdString = null;
	}

	/*
	 * You can use PWD(String s) param(s) The password
	 */
	public PWD(String pwdS) {
		this.pwdString = pwdS;
		pwdLength = pwdS.length();
		pwdBytes = new byte[pwdLength];
		pwdBytes = pwdS.getBytes();
	}

	public void setPassword(String pwdS) {
		pwdString = pwdS;
		pwdLength = pwdS.length();
		pwdBytes = new byte[pwdLength];
		pwdBytes = pwdS.getBytes();
	}

	/*
	 * return the changed password
	 */
	public String encode() {
		/*
		 * if ((pwdBytes.length%2)==0) { encodedBytes1 = new
		 * byte[pwdBytes.length]; encodedBytes2 = new byte[pwdBytes.length]; }
		 * else { encodedBytes1 = new byte[pwdBytes.length+1]; encodedBytes2 =
		 * new byte[pwdBytes.length-1]; }
		 */

		encodedBytes1 = new byte[30];
		encodedBytes2 = new byte[30];

		int n1 = 0, n2 = 0;
		for (int i = 0; i < pwdBytes.length; i++) {
			if ((i + 1) % 2 != 0) // 奇数位
			{
				encodedBytes1[n1++] = (byte) get32Hi((int) pwdBytes[i] * 4);
				// System.out.println(""+(int)pwdBytes[i]*4+":"+get32Hi((int)pwdBytes[i]*4));
				encodedBytes1[n1++] = (byte) get32Low((int) pwdBytes[i] * 4);
				// System.out.println(""+(int)pwdBytes[i]*4+":"+get32Low((int)pwdBytes[i]*4));
			} else // 偶数位
			{
				encodedBytes2[n2++] = (byte) get32Hi((int) pwdBytes[i] * 4);
				encodedBytes2[n2++] = (byte) get32Low((int) pwdBytes[i] * 4);
			}
		}

		while (n1 < 30)
			encodedBytes1[n1++] = (byte) getRandom(32);

		while (n2 < 30)
			encodedBytes2[n2++] = (byte) getRandom(32);

		pos1 = getRandom(pwdBytes.length);
		pos2 = getRandom(pwdBytes.length);
		// System.out.println(""+pos1+":"+pos2+"\n");
		sort(encodedBytes1, pos1);
		sort(encodedBytes2, pos2);
		check = (sumSqual(encodedBytes1) + sumSqual(encodedBytes2)) % 32;

		encodedArray = new byte[64];
		encodedArray[0] = (byte) pos1;
		encodedArray[1] = (byte) pos2;
		System.arraycopy(encodedBytes1, 0, encodedArray, 2,
				encodedBytes1.length);
		System.arraycopy(encodedBytes2, 0, encodedArray,
				2 + encodedBytes1.length, encodedBytes2.length);
		encodedArray[encodedArray.length - 2] = (byte) pwdLength;
		encodedArray[encodedArray.length - 1] = (byte) check;
		byte[] ps = new byte[encodedArray.length];

		for (int i = 0; i < encodedArray.length; i++)
			ps[i] = (byte) CHS[encodedArray[i]];

		return new String(ps);
	}

	/*
	 * return the old password form changed password param(s) the changed
	 * password
	 */
	public static String decode(String s) {
		if (s == null || s.trim().length() != 64) {
			return s;
		}
		byte[] sb = new byte[s.length()];
		byte[] bb = new byte[s.length()];

		sb = s.getBytes();

		for (int i = 0; i < sb.length; i++) {
			for (int j = 0; j < 32; j++) {
				if (((byte) CHS[j]) == sb[i]) {
					bb[i] = (byte) j;
					break;
				}
			}
		}

		int sl = (int) bb[bb.length - 2];
		int p1 = (int) bb[0];
		int p2 = (int) bb[1];

		byte[] bb1 = new byte[30];
		byte[] bb2 = new byte[30];

		@SuppressWarnings("unused")
        int bb1l, bb2l;
		if (sl % 2 == 0) {
			bb1l = sl;
			bb2l = sl;
		} else {
			bb1l = sl + 1;
			bb2l = sl - 1;
		}

		/*
		 * byte[] bb1,bb2; if (sl%2==0) { bb1=new byte[sl]; bb2=new byte[sl]; }
		 * else { bb1=new byte[sl+1]; bb2=new byte[sl-1]; }
		 */

		// System.out.println(""+bb1.length+":"+bb2.length);
		// System.out.println(""+p1+":"+p2);
		System.arraycopy(bb, 2, bb1, 0, bb1.length);
		System.arraycopy(bb, 2 + bb1.length, bb2, 0, bb2.length);

		unsort(bb1, p1);
		unsort(bb2, p2);
		byte[] oldb = new byte[sl];
		for (int i = 0; i < sl; i += 2) {
			oldb[i] = (byte) (getIntFrom32((int) bb1[i], (int) bb1[i + 1]) / 4);
			if (i + 1 < bb2l)
				oldb[i + 1] = (byte) (getIntFrom32((int) bb2[i],
						(int) bb2[i + 1]) / 4);
		}

		return new String(oldb);
	}

	public void test() {
		String ss = encode();
		System.out.println("The password be changed is :");
		System.out.println(ss);
		System.out.println("The source password is:");
		System.out.println(decode(ss));
	}

	private int sumSqual(byte[] b) {
		int sum = 0;
		for (int i = 0; i < b.length; i++)
			sum += (int) Math.pow(b[i], 2);
		return sum;
	}

	private int getRandom(int max) {
		return (int) (Math.random() * max);
	}

	private void sort(byte[] b, int pos) {
		if (pos < 0 || pos > b.length)
			System.out.println("pos is not validate");
		byte[] tmp = new byte[pos];
		System.arraycopy(b, 0, tmp, 0, pos);
		System.arraycopy(b, pos, b, 0, b.length - pos);
		System.arraycopy(tmp, 0, b, b.length - pos, pos);
	}

	private static void unsort(byte[] b, int pos) {
		if (pos < 0 || pos > b.length)
			System.out.println("pos is not validate");

		byte[] tmp = new byte[pos];
		System.arraycopy(b, b.length - pos, tmp, 0, pos);
		System.arraycopy(b, 0, b, pos, b.length - pos);
		System.arraycopy(tmp, 0, b, 0, pos);
	}

	private int get32Low(int num) {
		return (int) num % 32;
	}

	private int get32Hi(int num) {
		return (int) num / 32;
	}

	private static int getIntFrom32(int hi, int low) {
		return hi * 32 + low;
	}

	/**
	 * 模拟1000个线程并发，没有发现有将decode改为static之后存在并发问题
	 * 
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String args[]) throws InterruptedException {
				PWD pwd = new PWD("background.eis");
				System.out.println(pwd.encode());
				System.out
						.println(PWD
								.decode("4FNFMF865YRMEZVR6ENPDVMR6J353FDFVL5RVQGLYHEL68YQR8C7HLMXFRF3FSR7"));
//		for (int i = 0; i < 1000; i++) {
//			Thread t1 = new TestThread("" + i);
//			if (i % 2 == 0) {
//				Thread.yield();
//			}
//			t1.start();
//		}

	}
}

class TestThread extends Thread {
	private String str;

	public TestThread(String str) {
		this.str = str;
	}

	public void run() {
		PWD p = new PWD(str);
		String s = PWD.decode(p.encode());
//		System.out.println(str + ":#####:" + s);
		if(!str.equals(s)){
			System.out.println("发现源码解码后不一致 源码："+str+"；解码："+s);
		}
	}
}