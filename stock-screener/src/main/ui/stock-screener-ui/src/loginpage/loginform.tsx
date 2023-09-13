"use client"

import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import * as z from "zod"

import { Button } from "@/components/ui/button"
import {
    Form,
    FormControl,
    FormDescription,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import App from "@/App";

const FormSchema = z.object({
    username: z.string().min(2, {
        message: "Username must be at least 2 characters.",
    }).max(20, {
        message: "Username cannot be more than 20 characters"
    }),

    password: z.string().min(8, {
        message: "Password must be at least 8 characters"
    })
})

export function InputForm() {
    const form = useForm<z.infer<typeof FormSchema>>({
        resolver: zodResolver(FormSchema),
    })

    function onSubmit(data: z.infer<typeof FormSchema>) {
        postMessage(data, 'stock-screener/src/main/java/udo/elleh/WebSecurityConfig.java')
    }

    return (
        <div className="">
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)} className="">
                    <FormField
                        control={form.control}
                        name="username"
                        render={({ field }) => (
                            <FormItem>
                                <div className="flex flex-col items-start">
                                    <FormLabel>Username</FormLabel>
                                </div>
                                <FormControl>
                                    <Input placeholder="username" {...field} />
                                </FormControl>
                                
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="password"
                        render={({ field }) => (
                            <FormItem>
                                <div className="flex flex-col items-start">
                                    <FormLabel>Password</FormLabel>
                                </div>
                                <FormControl>
                                    <Input placeholder="password" {...field} />
                                </FormControl>
                                
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    <div className="p-5">

                    <Button type="submit">Submit</Button>
                    </div>
                </form>
            </Form>
        </div>
    )
}
