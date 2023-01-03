#include <jni.h>

#include "m3/wasm3.h"
#include "m3/m3_api_libc.h"

#define FATAL(msg, ...) { printf("Fatal: " msg "\n", ##__VA_ARGS__); return; }
#define FATAL0(msg, ...) { printf("Fatal: " msg "\n", ##__VA_ARGS__); return 0; }

IM3Function wasm_fibonacci;

JNIEXPORT void JNICALL
Java_com_example_hellowasm3_Wasm_00024Companion_loadWasm(JNIEnv *env, jobject thiz,
                                                         jbyteArray wasm) {
    M3Result result = m3Err_none;
    printf("Loading WebAssembly...\n");

    IM3Environment m3_env = m3_NewEnvironment();
    if (!env) FATAL("m3_NewEnvironment failed");

    IM3Runtime runtime = m3_NewRuntime(m3_env, 8192, NULL);
    if (!runtime) FATAL("m3_NewRuntime failed");

    jbyte buf[1024*1024];
    jint length = (*env)->GetArrayLength(env, wasm);
    (*env)->GetByteArrayRegion(env, wasm, 0, length, buf);
    IM3Module module;
    result = m3_ParseModule(m3_env, &module, buf, length);
    if (result) FATAL("m3_ParseModule: %s", result);

    result = m3_LoadModule(runtime, module);
    if (result) FATAL("m3_LoadModule: %s", result);

    result = m3_LinkLibC(module);
    if (result) FATAL("m3_LinkLibC: %s", result);

    result = m3_FindFunction(&wasm_fibonacci, runtime, "fibonacci");
    if (result) FATAL("m3_FindFunction: %s", result);
}

JNIEXPORT jint JNICALL
Java_com_example_hellowasm3_Wasm_00024Companion_wasmFibonacci(JNIEnv *env, jobject thiz, jint num) {
    M3Result result = m3Err_none;

    result = m3_CallV(wasm_fibonacci, num);
    if (result) FATAL0("m3_CallV: %s", result);

    int value = 0;
    result = m3_GetResultsV(wasm_fibonacci, &value);
    if (result) FATAL0("m3_GetResultsV: %s", result);

    return value;
}