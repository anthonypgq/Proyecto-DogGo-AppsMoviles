
import com.epn.doggo.LoginRequest
import com.epn.doggo.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("usuario/login")
    fun login(@Body request: LoginRequest): Call<Void>

    @POST("usuario/registro")
    fun register(@Body request: RegisterRequest): Call<Void>

}

