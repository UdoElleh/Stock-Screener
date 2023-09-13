import React from 'react'
import { Button } from '../components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { InputForm } from './loginform'




export default function LoginPage() {
  return (
    <div className='w-[80%] flex  justify-center'>
      <CardWithLoginForm></CardWithLoginForm>
    </div>
  )
}

export function CardWithLoginForm() {
  return (
    <Card className='w-full mx-0 flex flex-col items-center'>
      <div className=''>

      <CardHeader className=''>
        <div className=''>

        <CardTitle className=''>Login</CardTitle>
        <CardDescription>Login to your account</CardDescription>
        </div>
      </CardHeader>
      </div>
      <CardContent className=''>
        <p className='items-center'>
          <InputForm></InputForm>
        </p>
      </CardContent>
      <CardFooter>
        <p></p>
      </CardFooter>
    </Card>
  )
}
