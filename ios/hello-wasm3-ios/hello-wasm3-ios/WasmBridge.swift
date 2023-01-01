//
//  WasmBridge.swift
//  hello-wasm3-ios
//
//  Created by 小島和範 on 2023/01/01.
//

import Foundation

class WasmBridge: Identifiable {
}


extension WasmBridge {
    static func load() {
        let path = Bundle.main.path(forResource: "helloworld_bg", ofType: "wasm")
        let data: NSData? = try? NSData(contentsOfFile: path!, options: .uncached)
        let len = data!.length
        let buffer = UnsafeMutablePointer<UInt8>.allocate(capacity: len)
        data!.getBytes(buffer, length: len)
        launchWasm(buffer, UInt32(len))
    }
    
    static func fibonacci(n :UInt32) -> UInt32 {
        return wasm_fibonacci(n)
    }
}
