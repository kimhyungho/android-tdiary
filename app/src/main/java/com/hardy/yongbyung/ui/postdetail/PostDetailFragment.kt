package com.hardy.yongbyung.ui.postdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentPostDetailBinding
import com.hardy.yongbyung.dialog.AlertDialog
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : BaseViewModelFragment<FragmentPostDetailBinding, PostDetailViewModel>(
    R.layout.fragment_post_detail
), OnMapReadyCallback {
    override val viewModel: PostDetailViewModel by viewModels()

    private var mNaverMap: NaverMap? = null
    private var mapView: MapView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.map_fragment)
        mapView?.getMapAsync(this)
        mapView?.onCreate(savedInstanceState)

        with(viewDataBinding) {
            backButton.setOnClickListener {
                navController.popBackStack()
            }

            menuButton.setOnClickListener {
                val dialog = AlertDialog.newInstance(
                    "삭제 하시겠습니까?", "취소", "삭제",
                    onPositiveButtonClick = { viewModel?.delete() }
                )
                dialog.show(childFragmentManager, AlertDialog.TAG)
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenCreated {
                showBack.collect {
                    if (it != null) navController.popBackStack()
                }
            }

            lifecycleScope.launchWhenStarted {
                error.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        mNaverMap = naverMap.apply {
            with(uiSettings) {
                isCompassEnabled = false
                isScaleBarEnabled = false
                isLogoClickEnabled = false
                isZoomControlEnabled = false
                isTiltGesturesEnabled = false
                isLocationButtonEnabled = false
                isRotateGesturesEnabled = false
                isZoomGesturesEnabled = false
                isScrollGesturesEnabled = false
                isIndoorLevelPickerEnabled = false
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenCreated {
                post.collect {
                    val latLng =
                        LatLng(it?.place?.y ?: return@collect, it.place.x ?: return@collect)
                    mNaverMap?.moveCamera(CameraUpdate.scrollTo(latLng))
                    Marker(latLng).map = mNaverMap
                }
            }
        }
    }

    override fun onDestroyView() {
        mapView?.onDestroy()
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}