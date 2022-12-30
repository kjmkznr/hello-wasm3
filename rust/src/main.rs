use wasm3::Environment;
use wasm3::Module;

fn main() {
    let env = Environment::new().expect("Unable to create environment");
    let rt = env
        .create_runtime(1024 * 60)
        .expect("Unable to create runtime");
    let module = Module::parse(
        &env,
        &include_bytes!("/Users/kkojima/src/github.com/aralroca/helloworld-wasm-rust/pkg/helloworld_bg.wasm")[..]
        )
        .expect("Unable to parse module");

    let module = rt.load_module(module).expect("Unable to load module");
    let func = module
        .find_function::<u32, u32>("fibonacci")
        .expect("Unable to find function");
    println!("Wasm says that fibonacci 10 is {}", func.call(10).unwrap())
}