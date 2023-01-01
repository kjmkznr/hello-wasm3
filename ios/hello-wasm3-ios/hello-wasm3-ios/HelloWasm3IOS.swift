//
//  hello_wasm3_iosApp.swift
//  hello-wasm3-ios
//
//  Created by 小島和範 on 2022/12/31.
//

import SwiftUI

@main
struct HelloWasm3IOS: App {
    
    init() {
        WasmBridge.load()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

