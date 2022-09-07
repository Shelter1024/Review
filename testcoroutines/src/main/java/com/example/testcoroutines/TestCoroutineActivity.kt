package com.example.testcoroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.suspendCoroutine

class TestCoroutineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_coroutine)

//        //协程启动方式
//        //1.不推荐 协程的生命周期与app相同
//        GlobalScope.launch {
//            Log.d(
//                "Shelter",
//                "TestCoroutineActivity GlobalScope thread = ${Thread.currentThread().name}"
//            )
//        }
//        //2.推荐使用 通过coroutineContext管理和控制协程的生命周期
//        val coroutineContext = Dispatchers.Default + EmptyCoroutineContext
//        val coroutineScope = CoroutineScope(coroutineContext)
//        coroutineScope.launch {
//            Log.d(
//                "Shelter",
//                "TestCoroutineActivity coroutineScope.launch thread = ${Thread.currentThread().name}"
//            )
//        }
//        //3.async启动 async启动的Job是Deferred类型，它可以有返回结果，通过await方法获取
//        val deferred = coroutineScope.async {
//            Log.d(
//                "Shelter",
//                "TestCoroutineActivity coroutineScope.async thread = ${Thread.currentThread().name}"
//            )
//        }
//        //deferred.await()
//        //Log.d("Shelter", "TestCoroutineActivity onCreate $deferred")
//        //4.阻塞式 适用于单元测试的场景
//        runBlocking {
//            Log.d(
//                "Shelter",
//                "TestCoroutineActivity runBlocking thread = ${Thread.currentThread().name}"
//            )
//        }
//
//
//        runBlocking {
//            val job = async {
//                delay(5000)
//                "Shelter"
//            }
//            println("result：${job.await()}")
//        }
//
//        val launchWhenStarted = lifecycleScope.launchWhenStarted {
//            println("Shelter thread: ${Thread.currentThread().name}")
//        }
//        launchWhenStarted.start()
//
//        main()

        testCoroutine()
    }

//    suspend fun massiveRun(action: suspend () -> Unit) {
//        val n = 100  // 启动的协程数量
//        val k = 1000 // 每个协程重复执行同一动作的次数
//        val time = measureTimeMillis {
//            coroutineScope { // 协程的作用域
//                repeat(n) {
//                    launch {
//                        println("Shelter coroutineScope thread：${Thread.currentThread().name}")
//                        repeat(k) { action() }
//                    }
//                }
//            }
//        }
//        println("Shelter Completed ${n * k} actions in $time ms")
//    }
//
//    var counter = 0
//    val counterContext = newSingleThreadContext("CoroutineContext")
//    val mutex = Mutex()
//    val counter2 = AtomicInteger()
//
//    fun main() = runBlocking {
//        withContext(Dispatchers.Default) {
//            println("Shelter withContext thread：${Thread.currentThread().name}")
//            //方式一：非线程安全
////            massiveRun {
////                counter++
////            }
//            //方式二：线程安全 对共享数据操作限制在单线程中
////            massiveRun {
////                withContext(counterContext) {
////                    counter++
////                }
////            }
//            //方式三：线程安全 使用kotlin中的Mutex类
////            massiveRun {
////                mutex.withLock {
////                    counter++
////                }
////            }
//
//            massiveRun {
//                counter2.incrementAndGet()
//            }
//
//        }
//        println("Shelter Counter = $counter2")
//
//    }


    fun testCoroutine() {
        GlobalScope.launch {
            withContext(EmptyCoroutineContext) {

            }
            suspendCoroutine<Any> {  }
            coroutineScope {

            }
            launch {

            }
            val id = getId()
            val avatar = getAvatar(id)
            println("Shelter ${Thread.currentThread().name}- id:$id - avatar: $avatar")
        }

        val coroutineScope = CoroutineScope(EmptyCoroutineContext)
        coroutineScope.cancel(null)
    }

    private suspend fun getId(): String {
        return GlobalScope.async(Dispatchers.IO) {
            delay(1000)
            "123"
        }.await()
    }

    private suspend fun getAvatar(id: String): String {

        return GlobalScope.async(Dispatchers.IO) {
            delay(1000)
            "avatar-$id"
        }.await()
    }

}