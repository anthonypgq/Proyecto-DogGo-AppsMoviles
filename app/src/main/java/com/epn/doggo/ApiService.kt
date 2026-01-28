
import com.epn.doggo.GetPaseadoresResponse
import com.epn.doggo.GetUsuarioResponse
import com.epn.doggo.LoginRequest
import com.epn.doggo.LoginResponse
import com.epn.doggo.MascotasPorDuenoResponse
import com.epn.doggo.RegisterPetRequest
import com.epn.doggo.RegisterPetResponse
import com.epn.doggo.RegisterRequest
import com.epn.doggo.RegisterResponse
import com.epn.doggo.SolicitarPaseoRequest
import com.epn.doggo.SolicitarPaseoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("usuario/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("usuario/registro")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("mascota/registro")
    fun registerPet(@Body request: RegisterPetRequest): Call<RegisterPetResponse>

    @GET("usuario/{usuario_id}")
    fun getUsuario(@Path("usuario_id") usuarioId: String): Call<GetUsuarioResponse>

    @GET("usuario/")
    fun getPaseadores(): Call<GetPaseadoresResponse>

    @GET("mascota/dueno/{dueno_id}")
    fun getMascotasPorDueno(@Path("dueno_id") duenoId: String): Call<MascotasPorDuenoResponse>


    @POST("paseo/solicitar")
    fun solicitarPaseo(@Body request: SolicitarPaseoRequest): Call<SolicitarPaseoResponse>

}

