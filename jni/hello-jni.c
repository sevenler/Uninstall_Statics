/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <jni.h>

#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <android/log.h>
#include <unistd.h>

/* 宏定义begin */
//清0宏
#define MEM_ZERO(pDest, destSize) memset(pDest, 0, destSize)

//LOG宏定义
#define LOG_INFO(tag, msg) __android_log_write(ANDROID_LOG_INFO, tag, msg)
#define LOG_DEBUG(tag, msg) __android_log_write(ANDROID_LOG_DEBUG, tag, msg)
#define LOG_WARN(tag, msg) __android_log_write(ANDROID_LOG_WARN, tag, msg)
#define LOG_ERROR(tag, msg) __android_log_write(ANDROID_LOG_ERROR, tag, msg)


/* 内全局变量begin */
static char c_TAG[] = "onEvent";
static jboolean b_IS_COPY = JNI_TRUE;

jstring
Java_com_example_untitled_MyActivity_init( JNIEnv* env,
                                                 jobject thiz )
{

 jstring tag = (*env)->NewStringUTF(env, c_TAG);

      //初始化log
      LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY)
              , (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, "init OK"), &b_IS_COPY));

      //fork子进程，以执行轮询任务
      pid_t pid = fork();
      if (pid < 0)
      {
          //出错log
          LOG_ERROR((*env)->GetStringUTFChars(env, tag, &b_IS_COPY)
                  , (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, "fork error !!!"), &b_IS_COPY));
      }
      else if (pid == 0)
      {
          //子进程轮询"/data/data/com.example.untitled"目录是否存在，若不存在则说明已被卸载
         while (1)
          {
              FILE *p_file = fopen("/data/data/com.example.untitled", "r");
              if (p_file != NULL)
              {
                  fclose(p_file);

                  //目录存在log
                  LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY)
                              , (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, "I'm OK !!!"), &b_IS_COPY));

                  sleep(1);
              }
              else
              {
                  //目录不存在log
                  LOG_DEBUG((*env)->GetStringUTFChars(env, tag, &b_IS_COPY)
                              , (*env)->GetStringUTFChars(env, (*env)->NewStringUTF(env, "I'm NOT OK !!!"), &b_IS_COPY));

                  //执行命令am start -a android.intent.action.VIEW -d https://www.google.com
                  execlp("am", "am", "start", "-a", "android.intent.action.VIEW", "-d", "https://www.google.com", (char *)NULL);

                    //4.2以上的系统由于用户权限管理更严格，需要加上 --user 0
                  //execlp("am", "am", "start","--user", "0" ,"-a", "android.intent.action.VIEW", "-d", "https://www.google.com, (char *)NULL);
              }
          }
      }
      else
      {
          //父进程直接退出，使子进程被init进程领养，以避免子进程僵死
      }
   return (*env)->NewStringUTF(env, "Hello from JNI !");
}
