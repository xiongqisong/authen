package com.xqs.web;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Result<T> implements Serializable {
	private ReturnCode code;
	private String msg;
	private T data;

	public Result(ReturnCode code) {
		this(code, null, null);
	}

	public static Result success() {
		return new Result(ReturnCode.suc);
	}

	public static Result sucWithData(Object data) {
		Result result = new Result(ReturnCode.suc, "", data);
		return result;
	}

	public static Result errorWithMsg(String msg) {
		Result result = new Result(ReturnCode.error, msg, null);
		return result;
	}

	public static void main(String[] args) {
		Result<String> obj = new Result<String>(ReturnCode.suc, null, "hhe");
		System.out.println(JSON.toJSONString(obj));
	}
}
