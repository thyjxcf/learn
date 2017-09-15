package net.zdsoft.eis.frame.dao;

//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//import oracle.jdbc.OracleTypes;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * Oracle存储过程类, 负责调用Oracle存储过程。
 * 典型使用方式：
 * 假设Oracle存储过程定义如下：
 * create or replace procedure test_procedure(
 *     user_no in int,
 *     user_name in varchar2,
 *     user_password in varchar2,
 *     user_result out int,------传出参数
 *     user_result2 out varchar2------传出参数
 *    )AS...
 *   
 * 那么，可以采用如下方式获取返回值：
 * Connection connection = getConnection(...);
 * String procedureName = "test_procedure";
 * Object[] arguments = {new Integer(1),"zhuhf","123456",0,""};
 * Integer[] outArgsIndex = {3,4};
 * OracleProcedure oracleProcedure = 
 * 				new OracleProcedure(connection,procedureName,arguments,outArgsIndex);
 * oracleProcedure.callProcedure();
 * System.out.println(oracleProcedure.getProcedureResultByOutArgIndex(3));
 * System.out.println(oracleProcedure.getProcedureResultByOutArgIndex(4));
 * 
 * @author zhuhf
 */
public class OracleProcedure {

//	private static final Logger log = LoggerFactory.getLogger(OracleProcedure.class); 
//	
//	private Connection connection;
//	
//	private Object[] procedureResult;
//	
//	private Object[] args;
//	
//	private Set<Integer> outArgsIndexSet;
//	
//	private String procedureSQL;
//	
//	/**
//	 * JAVA类型到oracle数据类型的映射
//	 */
//	private static Map<Class<?>,Integer> standardTypeToOracleTypeMap;
//	
//	static{
//		standardTypeToOracleTypeMap = new HashMap<Class<?>,Integer>();
//		standardTypeToOracleTypeMap.put(Integer.class,OracleTypes.INTEGER);
//		standardTypeToOracleTypeMap.put(Long.class,OracleTypes.NUMBER);
//		standardTypeToOracleTypeMap.put(String.class,OracleTypes.VARCHAR);
//	}
//	
//	/**
//	 * 创建Oracle存储过程的对象。
//	 * @param connection oracle数据库连接
//	 * @param procedureName oracle存储过程名
//	 * @param args 存储过程参数列表,
//	 * 			注意如果是传出参数，将忽略它具体值，但是它的类型必须与数据库中类型相匹配
//	 * 			例如：Integer 与 Integer ,String 与 varchar2.
//	 * @param outArgsIndex 传出参数的下标
//	 */
//	public OracleProcedure(Connection connection,String procedureName,Object[] args, Integer[] outArgsIndex){
//		this.connection = connection;
//		this.args = args;
//		this.outArgsIndexSet = new HashSet<Integer>();
//		for (int i = 0 ; i < outArgsIndex.length ; i++) {
//			this.outArgsIndexSet.add(outArgsIndex[i]);
//		}
//		
//		StringBuilder sql = new StringBuilder();
//		sql.append("{ CALL ").append(procedureName.toUpperCase()).append(
//		"(");
//		for (int i = 0; i < args.length; i++) {
//			if (i == (args.length - 1)) {
//				sql.append("? )}");
//			} else {
//				sql.append("?,");
//			}
//		}
//		procedureSQL = sql.toString();
//	}
//	
//	private  CallableStatement getCallableStatement(){
//		CallableStatement cstmt = null;
//		try {
//			cstmt = connection.prepareCall(procedureSQL);
//		} catch (SQLException e) {
//			log.error("prepareCall failed with sql " + procedureSQL, e);
//		}
//		return cstmt;
//	}
//	
//	private boolean isOutArgument(Integer argIndex){
//		return outArgsIndexSet.contains(argIndex);
//	}
//	
//	private boolean hasOutArguments(){
//		return outArgsIndexSet.size() != 0;
//	}
//	
//	private  void setOracleProcedureArgs(CallableStatement cstmt,
//			Object arg, int argIndex, boolean isOutArg) {
//		try {
//			if (isOutArg) {
//				cstmt.registerOutParameter(argIndex+1, (Integer)standardTypeToOracleTypeMap.get(arg.getClass()));
//			} else {
//				cstmt.setObject(argIndex+1, arg);
//			}
//		} catch (SQLException e) {
//			log.error("set oracle procdure argument failed ,be sure their type are compatible ", e);
//		}
//
//	}
//
//	private Object[] fetchProcedureResult(CallableStatement cstmt){
//		
//		if (null == this.procedureResult){
//			this.procedureResult = new Object[args.length];
//			for (Iterator<Integer> it =outArgsIndexSet.iterator() ; it.hasNext();){
//				Integer index = it.next();
//				try {
//					procedureResult[index] = cstmt.getObject(index+1);
//				} catch (SQLException e) {
//					log.error("fetch procedure out argument failed .", e);
//				}
//			}
//		}
//		return this.procedureResult;
//	}
//	
//	/**
//	 * 调用Oracle存储过程，同时注销连接和事务
//	 */
//	public void callProcedure(){
//		CallableStatement cstmt = getCallableStatement();
//		
//		for (int i = 0; i < args.length; i++) {
//			setOracleProcedureArgs(cstmt, args[i],i, isOutArgument(i));
//		}
//		try {
//			cstmt.execute();
//			if (hasOutArguments()){
//				this.procedureResult = fetchProcedureResult(cstmt);
//			}
//			log.debug("call oracle procedure with :" + procedureSQL);
//		} catch (SQLException e) {
//			log.error("execute oracle procedure failed.", e);
//		}
//		finally{
//			try {
//				if (cstmt != null)
//					cstmt.close();
//			} catch (SQLException e) {
//				log.error("fail to destroy statement and connection.", e);
//			}
//		}
//	}
//	
//	/**
//	 * 获取Oracle存储过程的返回值，包含一个Object数组，该数组的长度与存储过程的参数个数
//	 * 相同，如果对应的下标是传出参数，那么该位置就为返回值，否则，该位置的值为null.
//	 * @return 存储过程的返回值数组
//	 */
//	public Object[] getProcedureResult(){
//		if (null == this.procedureResult) {
//			callProcedure();
//		}
//		return this.procedureResult;
//	}
//	
//	/**
//	 * 通过存储过程的下标获取存储过程返回值。
//	 * @param argIndex Oracle存储过程的传出参数的下标，注意该下标是整个参数中的下标，而
//	 * 不是传出参数的下标。
//	 * @return Oracle存储过程的传出参数的下表对应的值
//	 */
//	public Object getProcedureResultByOutArgIndex(Integer argIndex){
//		if (null == this.procedureResult) {
//			callProcedure();
//		}
//		return this.procedureResult[argIndex];
//	}
//	
}
