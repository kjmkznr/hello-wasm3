#include <fstream>
#include <iostream>
#include "wasm3_cpp.h"

int main(void)
{
    std::cout << "Loading WebAssembly..." << std::endl;

    try {
        wasm3::environment env;
        wasm3::runtime runtime = env.new_runtime(1024);
        const char* file_name = "../wasm/pkg/helloworld_bg.wasm";
        std::ifstream wasm_file(file_name, std::ios::binary | std::ios::in);
        if (!wasm_file.is_open()) {
            throw std::runtime_error("Failed to open wasm file");
        }
        wasm3::module mod = env.parse_module(wasm_file);
        runtime.load(mod);

        {
            wasm3::function fib_fn = runtime.find_function("fibonacci");
            auto res = fib_fn.call<int>(10);
            std::cout << "result: " << res << std::endl;
        }
    }
    catch(std::runtime_error &e) {
        std::cerr << "WASM3 error: " << e.what() << std::endl;
        return 1;
    }

    return 0;
}
