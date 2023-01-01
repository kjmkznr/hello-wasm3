//
//  ContentView.swift
//  hello-wasm3-ios
//
//  Created by 小島和範 on 2022/12/31.
//

import SwiftUI

struct ContentView: View {
    @State var resultText = "Fibonacci result ..."
    @State var inputNum = "10"
    
    var body: some View {
        VStack {
            TextField("", text: Binding(
                get: {inputNum},
                set: {inputNum = $0.filter{"0123456789".contains($0)}}))
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.all)
            
            Button("Fibonacci") {
                let n: UInt32 = NumberFormatter().number(from: "0" + inputNum) as! UInt32
                resultText = "Fibonacci ..."
                Task {
                    await calc(num: n)
                }
            }
            .padding(.all)
            .background(Color.blue)
            .foregroundColor(Color.white)
            
            Text(resultText)
        }
        .padding()
    }
    
    func calc(num :UInt32) async {
        let dispatchQueue = DispatchQueue(label: "QueueIdentification", qos: .background)
        dispatchQueue.async{
            let result = WasmBridge.fibonacci(n: num)
            DispatchQueue.main.async{
                resultText = String(format:"Fibonacci result %d", result)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
