//
//  wasm.h
//  hello-wasm3-ios
//
//  Created by 小島和範 on 2022/12/31.
//

#ifndef wasm_h
#define wasm_h

#include <stdio.h>

static void* loadWasm(void* ctx);

#ifdef __cplusplus
extern "C" {
#endif
void launchWasm(unsigned char*, unsigned int);
uint wasm_fibonacci(uint num);
#ifdef __cplusplus
}
#endif

#endif /* wasm_h */


